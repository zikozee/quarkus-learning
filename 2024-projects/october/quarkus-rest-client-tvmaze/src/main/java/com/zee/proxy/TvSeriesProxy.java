package com.zee.proxy;

import com.zee.model.TvSeries;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 19 Oct, 2024
 */

@Path("singlesearch")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient
public interface TvSeriesProxy {
    //https://api.tvmaze.com/singlesearch/shows
    @GET
    @Path("shows")
    TvSeries get(@QueryParam(value = "q")String title);
}
