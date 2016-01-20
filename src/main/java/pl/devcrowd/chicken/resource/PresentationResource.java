package pl.devcrowd.chicken.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pl.devcrowd.chicken.service.PresentationService;

@Path("/presentations")
@Produces(MediaType.APPLICATION_JSON)
public class PresentationResource {
	@Inject
	private PresentationService service;

	@POST
	@Path("/{id}/votes")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addVote(@PathParam("id") String id, String vote) {
		service.vote(id, vote);

		return Response.ok().build();
	}
}
