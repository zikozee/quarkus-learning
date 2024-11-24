package com.zee;

import jakarta.inject.Inject;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class MovieResource {
    
    private final MovieRepository movieRepository;

    @GET
    public Response getAll(){
        return Response.ok(movieRepository.listAll()).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") Long id){
        return movieRepository.findByIdOptional(id)
                .map(movie -> Response.ok(movie).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("title/{title}")
    public Response getByTitle(@PathParam("title") String title){
        return movieRepository.find("title", title)
                .singleResultOptional()
                .map(movie -> Response.ok(movie).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("country/{country}")
    public Response getByCountry(@PathParam("country") String country){
        List<Movie> movies  = movieRepository.findByCountry(country);
        return Response.ok(movies).build();
    }

    @Transactional
    @POST
    public Response create(Movie movie){
        movieRepository.persist(movie);
        if(movieRepository.isPersistent(movie)){
            return Response.created(URI.create("/movies/" + movie.getId())).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Transactional
    @PUT
    @Path("{id}")
    public Response update(Movie movie, @PathParam("id") long id){
        Optional<Movie> optionalMovie = movieRepository.findByIdOptional(id, LockModeType.PESSIMISTIC_WRITE);
        if(optionalMovie.isPresent()){
            int updatedRecords = movieRepository.updateMovie(movie, id);
            if(updatedRecords > 0){
                movie.setId(id);
                return Response.ok(movie).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Transactional
    @DELETE
    @Path("{id}")
    public Response deleteById(@PathParam("id") long id){
        boolean deleted = movieRepository.deleteById(id);

        return deleted ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }


}
