package pl.devcrowd.chicken.configuration;

public class MailConfiguration {
	private String host;

	private int port;

	private String registrationMailTemplate;

	private String registrationMailSubject;

	private String copy;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getRegistrationMailTemplate() {
		return registrationMailTemplate;
	}

	public void setRegistrationMailTemplate(String registrationMailTemplate) {
		this.registrationMailTemplate = registrationMailTemplate;
	}

	public String getRegistrationMailSubject() {
		return registrationMailSubject;
	}

	public void setRegistrationMailSubject(String registrationMailSubject) {
		this.registrationMailSubject = registrationMailSubject;
	}

    public String getCopy() {
        return copy;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }
}
