package pl.devcrowd.chicken.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class Presentation {
	private String id;
	@NotBlank
	@Size(max=80)
	private String title;
	@NotBlank
	@Size(max=500)
	private String description;
	private Language language;
	private int votes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public static enum Language {
		PL("PL"),
		ENG("ENG");
		private String value;
		private Language(String value) {
			this.value = value;
		}
		public String value() {
			return value;
		}
	}
}
