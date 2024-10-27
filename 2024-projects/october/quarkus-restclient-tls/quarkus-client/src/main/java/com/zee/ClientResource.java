package com.zee;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Path("/client")
public class ClientResource {

    @ConfigProperty(name = "url")
    URI serverURL;

    @ConfigProperty(name = "keyStore")
    String keyStoreFile;

    @ConfigProperty(name = "keyStorePassword")
    String keyStorePassword;

    @ConfigProperty(name = "trustStore")
    String trustStoreFile;

    @ConfigProperty(name = "trustStorePassword")
    String trustStorePassword;


    @Inject
    @RestClient
    Client client;

    @GET
    @Path("client")
    @Produces(MediaType.TEXT_PLAIN)
    public String callWithClient() {
        return client.call();
    }

    @GET
    @Path("clientBuilder")
    @Produces(MediaType.TEXT_PLAIN)
    public String callWithClientBuilder() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {


        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream inputStreamKeyStore = this.getClass().getClassLoader().getResourceAsStream(keyStoreFile);
        keyStore.load(inputStreamKeyStore, keyStorePassword.toCharArray());


        KeyStore truststore = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream inputStreamTrustStore = this.getClass().getClassLoader().getResourceAsStream(trustStoreFile);
        truststore.load(inputStreamTrustStore, trustStorePassword.toCharArray());

        Client clientBuild = RestClientBuilder.newBuilder()
                .baseUri(serverURL)
                .keyStore(keyStore, keyStorePassword)
                .trustStore(truststore)
                .build(Client.class);

        return clientBuild.call();
    }
}
