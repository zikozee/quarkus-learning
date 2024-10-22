package com.zee;

import io.smallrye.faulttolerance.api.RateLimit;
import io.smallrye.faulttolerance.api.RateLimitException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 22 Oct, 2024
 */

@ApplicationScoped
public class ToleranceService {
    private AtomicLong counter = new AtomicLong(0);


    @Inject
    @RestClient
    MyRemoteService myRemoteService;

    public Set<MyRemoteService.Extension> getWorkingExtensions(String extensionId){
        return myRemoteService.getExtensionsById(extensionId);
    }

    @Fallback(fallbackMethod = "getFallbackExtensions")
    public Set<MyRemoteService.Extension> getExtensionsFallback(String extensionId) {
        Set<MyRemoteService.Extension> extensions = myRemoteService.getExtensionsById(extensionId);
        if(extensions.isEmpty()) throw new RuntimeException("Some error occurred");
        return extensions;
    }

    public Set<MyRemoteService.Extension> getFallbackExtensions(String extensionId) {
        return Set.of(
                MyRemoteService.Extension.builder()
                        .id("io.quarkus:quarkus-rest")
                        .name("REST")
                        .shortName("resteasy-reactive")
                        .keywords(List.of("rest",
                                "fallback",
                                "fallback",
                                "fallback",
                                "fallback",
                                "fallback",
                                "fallback"
                                ))
                        .build()
        );
    }

    @Timeout(value = 2, unit = ChronoUnit.SECONDS)
//    @Fallback(fallbackMethod = "getFallbackExtensions")
    public Set<MyRemoteService.Extension> getExtensionsTimeout(String extensionId) {
        //upon timeout an exception is thrown :: org.eclipse.microprofile.faulttolerance.exceptions.Timeout
        try {
            Thread.sleep(Duration.ofSeconds(3));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return myRemoteService.getExtensionsById(extensionId);
    }

    @Timeout(value = 2, unit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod = "getFallbackExtensions", applyOn = {TimeoutException.class})
    public Set<MyRemoteService.Extension> getExtensionsTimeoutWithFallback(String extensionId) {
        //upon timeout an exception is thrown :: org.eclipse.microprofile.faulttolerance.exceptions.Timeout
        // since error occurs, we could catch Generic error or catch only Timeout with Fallback
        try {
            Thread.sleep(Duration.ofSeconds(3));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return myRemoteService.getExtensionsById(extensionId);
    }

     @CircuitBreaker(
             failOn = {RuntimeException.class}, // overriding default to fail only on RuntimeException.class
             requestVolumeThreshold = 10, // requests threshold to sample before Circuit breaker kicks in
             failureRatio = 0.6, // over 60% of requestVolumeThreshold will trigger the  circuit to open
             delay = 10,  // time in seconds before successThreshold is tested to see if requests are fully allowed or back to closes state
             delayUnit = ChronoUnit.SECONDS,
             successThreshold = 2
     )
     @Fallback(fallbackMethod = "getFallbackExtensions", applyOn = {RuntimeException.class})
    public Set<MyRemoteService.Extension> getExtensionsFallbackWithCircuitBreaker(String extensionId) {
         final long invocationNumber = counter.getAndIncrement();
         if(invocationNumber > 15) counter.set(0);
         System.out.println("count: " +invocationNumber);
         if(invocationNumber > 2) throw new RuntimeException("Some error occurred");

         return myRemoteService.getExtensionsById(extensionId);
    }


    @Timeout(value = 2, unit = ChronoUnit.SECONDS)
    @Retry(
        retryOn = {RuntimeException.class},
        maxRetries = 1
    )
    @Fallback(fallbackMethod = "getFallbackExtensions", applyOn = {TimeoutException.class})
    public Set<MyRemoteService.Extension> getExtensionsRetryWithFallback(String extensionId) {
        try {
            int rand = new SecureRandom().nextInt(1, 5);
            System.out.println("randomNumber: " + rand);
            Thread.sleep(Duration.ofSeconds(rand));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return myRemoteService.getExtensionsById(extensionId);
    }

    @RateLimit(value = 5, // number of calls
            window = 10, // 5 calls allowed within window (10) seconds    // else raise RateLimitException caught by fallback
            windowUnit = ChronoUnit.SECONDS
    )
    @Fallback(fallbackMethod = "getFallbackExtensions", applyOn = {RateLimitException.class})
    public Set<MyRemoteService.Extension> getExtensionsRateLimitWithFallback(String extensionId) {
        return myRemoteService.getExtensionsById(extensionId);
    }
}
