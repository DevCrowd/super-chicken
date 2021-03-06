package pl.devcrowd.chicken.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class Speaker {
	private String id;
	@NotBlank
	@Size(max=30)
	private String firstname;
	@NotBlank
	@Size(max=30)
	private String lastname;
	@NotBlank
	@Email
	@Size(max=50)
	private String email;
	@Size(max=500)
	private String description;
	private String picture;
	private TeeSize teeSize;
	@Size(max=100)
	private String origin;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TeeSize getTeeSize() {
		return teeSize;
	}

	public void setTeeSize(TeeSize teeSize) {
		this.teeSize = teeSize;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
