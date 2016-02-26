package pl.devcrowd.chicken.service;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import pl.devcrowd.chicken.dao.PresentationDao;
import pl.devcrowd.chicken.model.Presentation;

public class PresentationService {
	@Inject
	private PresentationDao dao;

	public List<Presentation> getFullPresentations() {
		return dao.getFullPresentations();
	}

	public List<Presentation> getPresentations() {
        return dao.getPresentations();
    }

	public List<Presentation> getSimplePresentations() {
	    List<Presentation> presentations = dao.getSimplePresentations();
        Collections.shuffle(presentations);
        return presentations;
    }

	public int getPresentationsCount() {
		return dao.getPresentationsCount();
	}
}
