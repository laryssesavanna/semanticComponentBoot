package br.imd.ufrn.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/school")
public class SchoolService {

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAll() {
		
		String resp = "Ok!";
		return Response.status(Response.Status.OK).entity(resp)
                .header("Access-Control-Allow-Origin", "*")
                .build();//200
	}
}
