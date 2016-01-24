package pl.devcrowd.chicken.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.skife.jdbi.v2.TransactionIsolationLevel;
import org.skife.jdbi.v2.sqlobject.Transaction;

import pl.devcrowd.chicken.dao.PresentationDao;
import pl.devcrowd.chicken.dao.SpeakerDao;
import pl.devcrowd.chicken.model.Speaker;

public class SpeakerService {
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
}
