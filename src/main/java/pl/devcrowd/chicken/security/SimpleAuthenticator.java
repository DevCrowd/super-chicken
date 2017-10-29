package pl.devcrowd.chicken.security;

import java.util.Optional;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class SimpleAuthenticator implements Authenticator<BasicCredentials, User> {
	private static final String SYSTEM_PASSWORD_VARIABLE_NAME = "SUPER_CHICKEN_PSSWD";

	@Override
	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
		if (credentials.getPassword() != null && System.getenv().get(SYSTEM_PASSWORD_VARIABLE_NAME).equals(credentials.getPassword())) {
			return Optional.of(new User(credentials.getUsername()));
		}

		return Optional.empty();
	}
}