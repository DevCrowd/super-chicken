package pl.devcrowd.chicken.resource;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pl.devcrowd.chicken.service.SpeakerService;

@Path("/speakers")
@Produces(MediaType.APPLICATION_JSON)
public class SpeakerResource {
	@Inject
	private SpeakerService service;

	@GET
	public Response getSpeakers() {
		return Response.ok().entity(service.getSpeakers()).build();
	}

	@RolesAllowed("ADMIN")
	@GET
	@Path("/full")
	public Response getFullSpeakers() {
		return Response.ok().entity(service.getFullSpeakers()).build();
	}

	@RolesAllowed("ADMIN")
	@GET
	@Path("/selected")
	public Response getSelectedProposals(@QueryParam("count") @DefaultValue("10") int count) {
		return Response.ok().entity(service.getSelectedSpeakers(count)).build();
	}

	@RolesAllowed("ADMIN")
    @POST
    @Path("/pictures/resize")
    public void resizePictures(@QueryParam("height") @DefaultValue("250") int height) {
        service.resizePictures(height);
    }

	@RolesAllowed("ADMIN")
    @POST
    @Path("/pictures/backups/restore")
    public void restoreBackupPictures() {
        service.restoreBackupPictures();
    }
}
