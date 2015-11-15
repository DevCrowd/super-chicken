package pl.devcrowd.chicken.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;

import com.google.common.base.Optional;

import pl.devcrowd.chicken.dao.SpeakerDao;

public class SpeakerService {
	@Inject
	private SpeakerDao dao;

	public void addSpeaker(String name, String surname, String description, Optional<InputStream> inputStream) throws IOException {
		byte[] photo = null;
		if (inputStream.isPresent()) {
			photo = IOUtils.toByteArray(inputStream.get());
		}

		dao.insert(UUID.randomUUID().toString(), name, surname, description, photo);
	}
}
