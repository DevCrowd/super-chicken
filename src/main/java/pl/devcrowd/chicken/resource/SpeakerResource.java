package pl.devcrowd.chicken.resource;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pl.devcrowd.chicken.service.SpeakerService;

@Path("/speakers")
@Produces(MediaType.APPLICATION_JSON)
public class SpeakerResource {
	@Inject
	private SpeakerService service;

	@PermitAll
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getSpeakers() {
		return Response.ok().entity(service.getSpeakers()).build();
	}
}
