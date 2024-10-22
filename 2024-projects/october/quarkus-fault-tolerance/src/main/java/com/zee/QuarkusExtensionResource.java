package com.zee;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;

@Path("tolerance")
public class QuarkusExtensionResource {

    @Inject
    ToleranceService toleranceService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response testWorkingEndpoint(@QueryParam("extensionId")String extensionId) {
        return Response.ok(toleranceService.getWorkingExtensions(extensionId)).build();
    }

    @GET
    @Path("fallback-test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testFallbackEndpoint(@QueryParam("extensionId")String extensionId) {
        return Response.ok(toleranceService.getExtensionsFallback(extensionId)).build();
    }

    @GET
    @Path("timeout-test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testTimeoutEndpoint(@QueryParam("extensionId")String extensionId) {
        try {
            return Response.ok(toleranceService.getExtensionsTimeout(extensionId)).build();
        }catch (TimeoutException e) {
            return Response.status(Response.Status.GATEWAY_TIMEOUT.getStatusCode(), "service timed out").build();
        }
    }

    @GET
    @Path("timeoutWithFallback-test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testTimeoutWithFallBack(@QueryParam("extensionId")String extensionId) {
        return Response.ok(toleranceService.getExtensionsTimeoutWithFallback(extensionId)).build();
    }

    @GET
    @Path("fallbackWithCircuitBreaker-test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testFallbackWithCircuitBreaker(@QueryParam("extensionId")String extensionId) {
        return Response.ok(toleranceService.getExtensionsFallbackWithCircuitBreaker(extensionId)).build();
    }

    @GET
    @Path("retryWithFallback-test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testRetryWithFallback(@QueryParam("extensionId")String extensionId) {
        return Response.ok(toleranceService.getExtensionsRetryWithFallback(extensionId)).build();
    }

    @GET
    @Path("rateLimitWithFallback-test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testRateLimitWithFallback(@QueryParam("extensionId")String extensionId) {
        return Response.ok(toleranceService.getExtensionsRateLimitWithFallback(extensionId)).build();
    }
}
