package pl.devcrowd.chicken.security;

import io.dropwizard.auth.Authorizer;

public class SimpleAuthorizer implements Authorizer<User> {
	private static final String ADMIN_ROLE_NAME = "ADMIN";
	private static final String SYSTEM_USER_NAME = "chicker";

	@Override
	public boolean authorize(User user, String role) {
		return user.getName().equals(SYSTEM_USER_NAME) && role.equals(ADMIN_ROLE_NAME);
	}
}