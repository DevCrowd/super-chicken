package pl.devcrowd.chicken.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.skife.jdbi.v2.TransactionIsolationLevel;
import org.skife.jdbi.v2.sqlobject.Transaction;

import pl.devcrowd.chicken.dao.PresentationDao;
import pl.devcrowd.chicken.dao.SpeakerDao;
import pl.devcrowd.chicken.model.Speaker;

public class SpeakerService {
    private static final int MAX_IMAGE_BYTE_SIZE = 200 * 1024;

	@Inject
	private SpeakerDao dao;
	@Inject
	private PresentationDao presentationDao;

	@Transaction(TransactionIsolationLevel.REPEATABLE_READ)
	public List<Speaker> getSpeakers() {
		return dao.getSimpleSpeakers();
	}

	@Transaction(TransactionIsolationLevel.REPEATABLE_READ)
	public List<Speaker> getFullSpeakers() {
		return dao.getSpeakers();
	}

	@Transaction(TransactionIsolationLevel.REPEATABLE_READ)
	public List<Speaker> getSelectedSpeakers(int count) {
		return presentationDao.getSelectedPresentations(count).stream()
			.map(p -> dao.getSpeakersForPresentation(p.getId()))
			.flatMap(s -> s.stream())
			.collect(Collectors.toList());
	}

	@Transaction
    public void resizePictures(int height) {
	    dao.getSimpleSpeakers().forEach(s -> resizePictureWithBackup(s, height));
    }

	@Transaction
    public void restoreBackupPictures() {
        dao.restoreBackupPictures();
    }

	private void resizePictureWithBackup(Speaker speaker, int height) {
	    if (speaker.getPicture() == null) {
	        return;
	    }

	    dao.savePictureBackup(speaker.getId(), speaker.getPicture());
	    dao.saveNewPicture(speaker.getId(), resizeImage(height, speaker.getPicture()));
	}

	private String resizeImage(int defaultHeight, String inputImage) {
	    if (inputImage == null) {
	        return null;
	    }

	    String result = inputImage;

        try (ByteArrayInputStream inputImageInputStream = new ByteArrayInputStream(Base64.getDecoder().decode(inputImage))) {
            BufferedImage bufferedInputImage = ImageIO.read(inputImageInputStream);

            if (bufferedInputImage.getHeight() > defaultHeight && getImageSize(bufferedInputImage) > MAX_IMAGE_BYTE_SIZE) {
                Image scaledInstance = bufferedInputImage.getScaledInstance((defaultHeight*bufferedInputImage.getWidth()) / bufferedInputImage.getHeight(), defaultHeight, BufferedImage.SCALE_SMOOTH);
                BufferedImage outputImage = new BufferedImage(scaledInstance.getWidth(null), scaledInstance.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                outputImage.getGraphics().drawImage(scaledInstance, 0, 0 , null);

                try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                    ImageIO.write(outputImage, "png", byteArrayOutputStream);

                    result = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
                }
            }
        } catch (IOException e) {
            result = inputImage;
        }

        return result;
    }

	private int getImageSize(BufferedImage image) throws IOException {
	    try (ByteArrayOutputStream tmp = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", tmp);

            return tmp.size();
	    }
	}
}
