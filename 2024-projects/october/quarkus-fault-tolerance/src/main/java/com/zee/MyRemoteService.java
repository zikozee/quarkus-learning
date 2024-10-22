package com.zee;

import lombok.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import java.util.List;
import java.util.Set;


@RegisterRestClient
public interface MyRemoteService {

    @GET
    @Path("/extensions")
    Set<Extension> getExtensionsById(@QueryParam("id") String id);

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    class Extension {
        private String id;
        private String name;
        private String shortName;
        private List<String> keywords;
    }
}
