package pl.devcrowd.chicken.model;

import java.util.List;

import javax.validation.Valid;

public class Proposal {
	@Valid
	private List<Speaker> speakers;
	@Valid
	private List<Presentation> presenations;

	public Proposal() {}

	public Proposal(List<Speaker> speakers, List<Presentation> presenations) {
		this.speakers = speakers;
		this.presenations = presenations;
	}

	public List<Presentation> getPresenations() {
		return presenations;
	}

	public void setPresenations(List<Presentation> presenations) {
		this.presenations = presenations;
	}

	public List<Speaker> getSpeakers() {
		return speakers;
	}

	public void setSpeakers(List<Speaker> speakers) {
		this.speakers = speakers;
	}
}
