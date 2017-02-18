package pl.devcrowd.chicken.model;

import java.util.List;

import javax.validation.Valid;

public class Proposal {
	@Valid
	private List<Speaker> speakers;
	@Valid
	private List<Presentation> presentations;

	public Proposal() {}

	public Proposal(List<Speaker> speakers, List<Presentation> presentations) {
		this.speakers = speakers;
		this.presentations = presentations;
	}

	public List<Speaker> getSpeakers() {
		return speakers;
	}

	public void setSpeakers(List<Speaker> speakers) {
		this.speakers = speakers;
	}

	public List<Presentation> getPresentations() {
		return presentations;
	}

	public void setPresentations(List<Presentation> presentations) {
		this.presentations = presentations;
	}
}
