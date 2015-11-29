package pl.devcrowd.chicken.model;

import java.util.List;

public class Proposal {
	private List<Speaker> speakers;
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
