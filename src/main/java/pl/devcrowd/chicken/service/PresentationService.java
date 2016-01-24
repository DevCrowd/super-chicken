package pl.devcrowd.chicken.service;

import java.util.List;

import javax.inject.Inject;

import pl.devcrowd.chicken.dao.PresentationDao;
import pl.devcrowd.chicken.model.Presentation;

public class PresentationService {
	@Inject
	private PresentationDao dao;

	public void vote(String id, String vote) {
		if (isProperValue(vote)) {
			dao.vote(id, vote);
		} else {
			throw new IllegalArgumentException("Unsupported value");
		}
	}

	public List<Presentation> getFullPresentations() {
		return dao.getFullPresentations();
	}

	private boolean isProperValue(String vote) {
		try {
			if (Math.abs(new Integer(vote)) == 1) {
				return true;
			}

			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public int getPresentationsCount() {
		return dao.getPresentationsCount();
	}
}
