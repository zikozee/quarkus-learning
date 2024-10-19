package com.zee.proxy;

import com.zee.model.Episode;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 19 Oct, 2024
 */


@Path("shows")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient
public interface EpisodeProxy {
    // https://api.tvmaze.com/shows/1/episodes

    @GET
    @Path("{id}/episodes")
    List<Episode> get(@PathParam("id") Long id);
}
