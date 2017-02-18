package pl.devcrowd.chicken.resource;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pl.devcrowd.chicken.service.PresentationService;

@Path("/presentations")
@Produces(MediaType.APPLICATION_JSON)
public class PresentationResource {
	@Inject
	private PresentationService service;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getPresentations(@QueryParam("mode") @DefaultValue("0") int mode) {
        if (mode == 1) {
            return Response.ok(service.getSimplePresentations()).build();
        }

        return Response.ok(service.getPresentations()).build();
    }

	@RolesAllowed("ADMIN")
	@GET
	@Path("/full")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getFullPresentations() {
		return Response.ok(service.getFullPresentations()).build();
	}

	@GET
	@Path("/count")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getPresentationsCount() {
		return Response.ok(service.getPresentationsCount()).build();
	}
}
