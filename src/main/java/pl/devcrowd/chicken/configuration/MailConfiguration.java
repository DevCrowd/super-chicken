package pl.devcrowd.chicken.configuration;

public class MailConfiguration {
	private String host;
	private int port;
	private String registrationMailTemplate;
	private String registrationMailSubject;
	private String participantRegistrationMailTemplate;
    private String participantRegistrationMailSubject;
	private String fromAddress;

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

    public String getParticipantRegistrationMailTemplate() {
        return participantRegistrationMailTemplate;
    }

    public void setParticipantRegistrationMailTemplate(String participantRegistrationMailTemplate) {
        this.participantRegistrationMailTemplate = participantRegistrationMailTemplate;
    }

    public String getParticipantRegistrationMailSubject() {
        return participantRegistrationMailSubject;
    }

    public void setParticipantRegistrationMailSubject(String participantRegistrationMailSubject) {
        this.participantRegistrationMailSubject = participantRegistrationMailSubject;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }
}
