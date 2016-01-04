package pl.devcrowd.chicken.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.skife.jdbi.v2.TransactionIsolationLevel;
import org.skife.jdbi.v2.sqlobject.Transaction;

import pl.devcrowd.chicken.dao.PresentationDao;
import pl.devcrowd.chicken.dao.SpeakerDao;
import pl.devcrowd.chicken.model.Presentation;
import pl.devcrowd.chicken.model.Proposal;
import pl.devcrowd.chicken.model.Speaker;

public class ProposalService {
	@Inject
	private SpeakerDao speakerDao;
	@Inject
	private PresentationDao presentationDao;

	@Transaction
	public Proposal addProposal(Proposal proposal) {
		List<Speaker> speakers = proposal.getSpeakers().stream().map(this::addSpeaker).collect(Collectors.toList());
		List<Presentation> presentations = proposal.getPresenations().stream().map((p) -> addPresentation(p, speakers)).collect(Collectors.toList());

		return new Proposal(speakers, presentations);
	}

	@Transaction(TransactionIsolationLevel.REPEATABLE_READ)
	public List<Proposal> getProposals() {
		return presentationDao.getPresentations().stream()
				.map(p -> new Proposal(speakerDao.getSpeakersForPresentation(p.getId()), Arrays.asList(p)))
				.collect(Collectors.toList());
	}

	@Transaction(TransactionIsolationLevel.REPEATABLE_READ)
	public Proposal getRandomProposal() {
		Presentation randomPresentation = presentationDao.getRandomPresentation();
		List<Speaker> speakers = speakerDao.getSpeakersForPresentation(randomPresentation.getId());

		return new Proposal(speakers, Arrays.asList(randomPresentation));
	}

	@Transaction(TransactionIsolationLevel.REPEATABLE_READ)
	public List<Proposal> getSelectedProposals(int count) {
		return presentationDao.getSelectedPresentations(count).stream()
			.map(p -> new Proposal(speakerDao.getSpeakersForPresentation(p.getId()), Arrays.asList(p)))
			.collect(Collectors.toList());
	}

	private Speaker addSpeaker(Speaker speaker) {
		speaker.setId(UUID.randomUUID().toString());

		speakerDao.insert(speaker.getId(), speaker.getFirstname(), speaker.getLastname(), speaker.getEmail(), speaker.getDescription(),
				speaker.getPicture(), speaker.getTeeSize().value(), speaker.getOrigin());

		return speaker;
	}

	private Presentation addPresentation(Presentation presentation, List<Speaker> speakers) {
		String id = UUID.randomUUID().toString();
		presentation.setId(id);

		presentationDao.insert(id, presentation.getTitle(), presentation.getDescription(), presentation.getLanguage().value());
		speakers.forEach(s -> presentationDao.insertSpeakerRelation(id, s.getId()));

		return presentation;
	}
}
