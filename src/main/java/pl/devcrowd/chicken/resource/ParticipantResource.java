package pl.devcrowd.chicken.resource;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pl.devcrowd.chicken.model.Participant;
import pl.devcrowd.chicken.service.ParticipantService;

@Path("/participants")
public class ParticipantResource {
    @Inject
    private ParticipantService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addParticipant(Participant participant) {
        return Response.created(null).entity(service.addParticipant(participant)).build();
    }

    @POST
    @Path("/{id}/votes")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response vote(@PathParam("id") String id, List<String> presentationsIds) {
        service.vote(id, presentationsIds);

        return Response.ok().build();
    }

    @RolesAllowed("ADMIN")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getParticipants() {
        return Response.ok(service.getParticipants()).build();
    }

    @RolesAllowed("ADMIN")
    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setAttanded(@PathParam("id") String id) {
        service.setAttended(id);

        return Response.ok().build();
    }
}
