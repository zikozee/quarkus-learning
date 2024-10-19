package com.zee.proxy;

import com.zee.model.Episode;
import com.zee.model.TvSeries;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 19 Oct, 2024
 */

@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient
public interface TvSeriesEpisodesCombineProxy {

    @GET
    @Path("singlesearch/shows")
    TvSeries getSeries(@QueryParam(value = "q")String title);


    @GET
    @Path("shows/{id}/episodes")
    List<Episode> getEpisodes(@PathParam("id") Long id);
}
