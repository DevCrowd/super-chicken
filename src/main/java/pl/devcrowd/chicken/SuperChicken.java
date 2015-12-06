package pl.devcrowd.chicken;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import pl.devcrowd.chicken.configuration.DbIncludedConfiguration;
import pl.devcrowd.chicken.dao.PresentationDao;
import pl.devcrowd.chicken.dao.SpeakerDao;
import pl.devcrowd.chicken.service.PresentationService;
import pl.devcrowd.chicken.service.ProposalService;
import pl.devcrowd.chicken.service.SpeakerService;

public class SuperChicken extends Application<DbIncludedConfiguration> {
	public static void main(String[] args) throws Exception {
        new SuperChicken().run(args);
    }

	@Override
    public String getName() {
        return "super-chicken";
    }

	@Override
	public void run(DbIncludedConfiguration configuration, Environment environment) throws Exception {
		environment.jersey().packages("pl.devcrowd.chicken");

		final DBIFactory factory = new DBIFactory();
		final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");

		environment.jersey().register(new AbstractBinder() {
            @Override
            protected void configure() {
            	bind(jdbi.onDemand(SpeakerDao.class)).to(SpeakerDao.class);
            	bind(jdbi.onDemand(PresentationDao.class)).to(PresentationDao.class);
            	bind(SpeakerService.class).to(SpeakerService.class);
            	bind(ProposalService.class).to(ProposalService.class);
            	bind(PresentationService.class).to(PresentationService.class);
            }
		});
		environment.jersey().register(MultiPartFeature.class);

        configureCrossOriginFilter(environment.servlets().addFilter("CORS", CrossOriginFilter.class));
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
