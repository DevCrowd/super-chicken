package pl.devcrowd.chicken.resource;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pl.devcrowd.chicken.model.Proposal;
import pl.devcrowd.chicken.service.ProposalService;

@Path("/proposals")
@Produces(MediaType.APPLICATION_JSON)
public class ProposalResource {
	@Inject
	private ProposalService service;

	@RolesAllowed("ADMIN")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProposal(@Valid Proposal proposal) {
		return Response.created(null).entity(service.addProposal(proposal)).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getProposals() {
		return Response.ok().entity(service.getProposals()).build();
	}

	@GET
    @Path("/presentations/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getProposalByPresentationId(@PathParam("id") String id, @QueryParam("mode") @DefaultValue("0") int mode) {
	    if (mode == 1) {
	        return Response.ok().entity(service.getSimpleProposalByPropositionId(id)).build();
	    }

        return Response.ok().entity(service.getProposalByPropositionId(id)).build();
    }

	@GET
	@Path("/random")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRandomProposal() {
	    return Response.ok().entity(service.getRandomProposal()).build();
	}

	@RolesAllowed("ADMIN")
	@GET
	@Path("/full")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getFullProposals() {
		return Response.ok().entity(service.getFullProposals()).build();
	}

	@RolesAllowed("ADMIN")
	@GET
	@Path("/selected")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getSelectedProposals(@QueryParam("count") @DefaultValue("10") int count) {
		return Response.ok().entity(service.getSelectedProposals(count)).build();
	}
}
