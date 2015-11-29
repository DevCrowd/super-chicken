package pl.devcrowd.chicken.resource;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pl.devcrowd.chicken.model.Proposal;
import pl.devcrowd.chicken.service.ProposalService;

@Path("/proposals")
@Produces(MediaType.APPLICATION_JSON)
public class ProposalResource {
	@Inject
	private ProposalService service;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProposal(@Valid Proposal proposal) {
		return Response.created(null).entity(service.addProposal(proposal)).build();
	}

	@GET
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getProposal(@PathParam("id") Long id) {
		return null;
	}
}
