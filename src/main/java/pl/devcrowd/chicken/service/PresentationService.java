package pl.devcrowd.chicken.service;

import javax.inject.Inject;

import pl.devcrowd.chicken.dao.PresentationDao;

public class PresentationService {
	@Inject
	private PresentationDao dao;

	public void vote(long id, String vote) {
		if (isProperValue(vote)) {
			dao.vote(id, vote);
		} else {
			throw new IllegalArgumentException("Unsupported value");
		}
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
}
