package pl.devcrowd.chicken;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.skife.jdbi.v2.DBI;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import pl.devcrowd.chicken.configuration.MailConfiguration;
import pl.devcrowd.chicken.configuration.SuperChickenConfiguration;
import pl.devcrowd.chicken.dao.PresentationDao;
import pl.devcrowd.chicken.dao.ProposalDao;
import pl.devcrowd.chicken.dao.SpeakerDao;
import pl.devcrowd.chicken.security.SimpleAuthenticator;
import pl.devcrowd.chicken.security.SimpleAuthorizer;
import pl.devcrowd.chicken.security.User;
import pl.devcrowd.chicken.service.MailService;
import pl.devcrowd.chicken.service.PresentationService;
import pl.devcrowd.chicken.service.ProposalService;
import pl.devcrowd.chicken.service.SpeakerService;

public class SuperChicken extends Application<SuperChickenConfiguration> {
	public static void main(String[] args) throws Exception {
        new SuperChicken().run(args);
    }

	@Override
    public String getName() {
        return "super-chicken";
    }

	@Override
	public void run(SuperChickenConfiguration configuration, Environment environment) throws Exception {
		environment.jersey().packages("pl.devcrowd.chicken");

		final DBIFactory factory = new DBIFactory();
		final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");

		MailConfiguration mailConfiguration = configuration.getMail();
		MailService mailService = new MailService(mailConfiguration.getHost(), mailConfiguration.getPort(),
				System.getenv().getOrDefault("MAIL_SRV_USR", ""), System.getenv().getOrDefault("MAIL_SRV_PSSWD", ""));

		environment.jersey().register(new AbstractBinder() {
            @Override
            protected void configure() {
            	bind(jdbi.onDemand(SpeakerDao.class)).to(SpeakerDao.class);
            	bind(jdbi.onDemand(PresentationDao.class)).to(PresentationDao.class);
            	bind(jdbi.onDemand(ProposalDao.class)).to(ProposalDao.class);
            	bind(SpeakerService.class).to(SpeakerService.class);
            	bind(ProposalService.class).to(ProposalService.class);
            	bind(PresentationService.class).to(PresentationService.class);
            	bind(mailService).to(MailService.class);
            	bind(mailConfiguration).to(MailConfiguration.class);
            }
		});

        configureCrossOriginFilter(environment.servlets().addFilter("CORS", CrossOriginFilter.class));

        configureBasicSecurity(environment);
	}

	private void configureBasicSecurity(Environment environment) {
		environment.jersey().register(new AuthDynamicFeature(
			new BasicCredentialAuthFilter.Builder<User>()
				.setAuthenticator(new SimpleAuthenticator())
				.setAuthorizer(new SimpleAuthorizer())
				.setRealm("SUPER SECRET STUFF")
				.buildAuthFilter()));
		environment.jersey().register(RolesAllowedDynamicFeature.class);
	}

	private void configureCrossOriginFilter(FilterRegistration.Dynamic filter) {
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter("allowedOrigins", "*");
        filter.setInitParameter("allowedHeaders", "content-Type, accept, authorization, x-requested-with, content-length, origin");
        filter.setInitParameter("allowedMethods", "GET,PUT,POST,DELETE,OPTIONS,HEAD");
        filter.setInitParameter("allowCredentials", "true");
        filter.setInitParameter("exposedHeaders", "Location, Last-Modified, ETag, Date");
    }
}
