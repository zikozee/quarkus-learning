package com.zee;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/movies")
public class MovieResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(Movie.listAll()).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Integer id) {
        return Movie.findByIdOptional(id)
                .map(movie -> Response.ok(movie).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("country/{country}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByCountry(@PathParam("country") String country) {
        List<Movie> movies = Movie.list("SELECT m FROM Movie m WHERE m.country = ?1 ORDER By id DESC", country);
        return Response.ok(movies).build();
    }

    @GET
    @Path("title/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByTitle(@PathParam("title") String title) {
        return Movie.find("title", title)
                .singleResultOptional()
                .map(movie -> Response.ok(movie).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }


    @Transactional   // for wrap in a transaction
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMovie(Movie movie) {
        Movie.persist(movie);
        if (movie.isPersistent()){
            return Response.created(URI.create("/movies" + movie.id)).build();
//            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    @Path("{id}")
    public Response deleteById(@PathParam("id") long id) {
        boolean deleted = Movie.deleteById(id);
        if (deleted){
            return Response.noContent().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
