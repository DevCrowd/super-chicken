package pl.devcrowd.chicken.service;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);
	private String host;
	private int port;
	private String user;
	private String password;
    private String fromAddress;

	public MailService(String host, int port, String user, String password, String fromAddress) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
        this.fromAddress = fromAddress;
	}

	public void sendMail(String emailAddress, String subject, String message) {
		try {
			HtmlEmail email = new HtmlEmail();

			email.setHostName(host);
			email.setSmtpPort(port);
			email.setAuthenticator(new DefaultAuthenticator(user, password));
			email.setSSLOnConnect(true);
			email.setFrom(fromAddress);
			email.setSubject(subject);
			email.setHtmlMsg(message);
			email.addTo(emailAddress);
			email.setCharset("utf-8");

			email.send();
		} catch (EmailException e) {
			LOGGER.error("Failed to send mail", e);
		}
	}
}
