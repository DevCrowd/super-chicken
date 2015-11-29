package pl.devcrowd.chicken.service;

import java.util.List;

import javax.inject.Inject;

import pl.devcrowd.chicken.dao.SpeakerDao;
import pl.devcrowd.chicken.model.Speaker;

public class SpeakerService {
	@Inject
	private SpeakerDao dao;

	public List<Speaker> getSpeakers() {
		return dao.getSpeakers();
	}
}
