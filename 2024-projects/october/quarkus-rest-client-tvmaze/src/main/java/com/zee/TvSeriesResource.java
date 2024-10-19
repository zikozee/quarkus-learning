package com.zee;

import com.zee.model.Episode;
import com.zee.proxy.EpisodeProxy;
import com.zee.proxy.TvSeriesEpisodesCombineProxy;
import com.zee.proxy.TvSeriesProxy;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.zee.model.TvSeries;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 19 Oct, 2024
 */

@Path("tvseries")
public class TvSeriesResource {

    @Inject
    @RestClient
    TvSeriesProxy tvSeriesProxy;

    @Inject
    @RestClient
    EpisodeProxy episodeProxy;

    @Inject
    @RestClient
    TvSeriesEpisodesCombineProxy tvSeriesEpisodesCombineProxy;

//    private final List<TvSeries> tvSeries = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("title") String title) {
        TvSeries result = tvSeriesEpisodesCombineProxy.getSeries(title);
        List<Episode> episodes = tvSeriesEpisodesCombineProxy.getEpisodes(result.getId());
        return Response.ok(episodes).build();
    }

}
