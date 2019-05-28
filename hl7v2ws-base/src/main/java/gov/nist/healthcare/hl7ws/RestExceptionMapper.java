package gov.nist.healthcare.hl7ws;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RestExceptionMapper implements
		ExceptionMapper<WebApplicationException> {

	public Response toResponse(WebApplicationException exception) {

		return Response.status(exception.getResponse().getStatus())
				.entity("<ERROR>" + exception.getMessage() + "</ERROR>")
				.type("application/xml").build();
	}
}
