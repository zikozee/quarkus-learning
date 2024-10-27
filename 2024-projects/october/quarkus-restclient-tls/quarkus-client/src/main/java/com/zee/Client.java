package com.zee;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 23 Oct, 2024
 */

@Path("/")
@RegisterRestClient
public interface Client {

    @GET
    @Path("server")
    @Produces(MediaType.TEXT_PLAIN)
    String call();
}
