package pl.devcrowd.chicken.resource;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.google.common.base.Optional;

import pl.devcrowd.chicken.service.SpeakerService;

@Path("/speakers")
@Produces(MediaType.APPLICATION_JSON)
public class SpeakerResource {
	@Inject
	private SpeakerService service;

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addSpeaker(@FormDataParam("name") String name, @FormDataParam("surname") String surname, @FormDataParam("description") String description,
			@FormDataParam("file") Optional<InputStream> inputStream,
			@FormDataParam("file") Optional<FormDataContentDisposition> contentDispositionHeader) throws IOException {
		service.addSpeaker(name, surname, description, inputStream);

		return Response.created(null).build();
	}
}
