package com.zee;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 14 Oct, 2024
 */

@Path("/movies")
@Tag(name = "Movie Resource", description = "Movie REST APIs")
public class MovieResource {

    public static List<Movie> movies = new ArrayList<>();


    @Operation(
            summary = "Get Movies",
            operationId = "getMovies",
            description = "Get all movies in the list"
    )
    @APIResponse(
            responseCode = "200",
            description = "Operation Completed",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))

    )
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getMovies(){
        return Response.ok(movies).build();
    }

    @Operation(
            summary = "Count Movies",
            operationId = "countMovies",
            description = "Size of Movies list"
    )
    @APIResponse(
            responseCode = "200",
            description = "Operation Completed",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Integer.class))

    )
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/size")
    public Integer countMovies(){
        return movies.size();
    }

    @Operation(
            summary = "Create a new Movie",
            operationId = "createMovies",
            description = "Create a new movie to add inside the list"
    )
    @APIResponse(
            responseCode = "201",
            description = "Movie Created",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))

    )
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createMovie(
            @RequestBody( //openapi for documenting request
                    description = "Movie to create",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Movie.class))
            )
            Movie newMovie){
        movies.add(newMovie);
        return Response.status(Response.Status.CREATED).entity(movies).build();
    }


    @PUT
    @Path("/2")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateMovie(MovieUpdate movieUpdate){
//        if(movies.isEmpty() || movies.stream().noneMatch(movie -> movie.title().equalsIgnoreCase(movieUpdate.oldMovie)))
//            return Response.status(Response.Status.BAD_REQUEST).build();
//
//        movies.remove(movieUpdate.oldMovie);
//        movies.add(movieUpdate.newMovie);
        return Response.ok(movies).build();
    }

    @Operation(
            summary = "Update an existing movie",
            operationId = "updateMovie2",
            description = "Update a movie inside the list"
    )
    @APIResponse(
            responseCode = "200",
            description = "Movie Updated",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))

    )
    @PUT
    @Path("{id}/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMovie2(
            @Parameter(
                    description = "Movie id",
                    required = true
            )
            @PathParam("id") Long id,

            @Parameter(
                    description = "Movie title",
                    required = true
            )
            @PathParam("title")String title){

        movies = movies.stream()
                .peek(movie -> {
                    if(Objects.equals(movie.getId(), id)){
                        movie.setTitle(title);
                    }
                })
//                .toList(); this converts to an unmodifiableList
                .collect(Collectors.toList());
        return Response.ok(movies).build();
    }

    @Operation(
            summary = "Delete an existing movie",
            operationId = "deleteMovie",
            description = "Delete a movie inside the list"
    )
    @APIResponse(
            responseCode = "204",
            description = "Movie not valid",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))

    )
    @APIResponse(
            responseCode = "400",
            description = "Movie deleted",
            content = @Content(mediaType = MediaType.TEXT_PLAIN)

    )
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteMovie(@PathParam("id")Long id){
        Optional<Movie> movieToDelete = movies.stream().filter(movie -> movie.getId().equals(id)).findFirst();

        boolean removed = false;
        if(movieToDelete.isPresent()){
            removed = movies.remove(movieToDelete.get());
        }
        if(removed){
            return Response.ok(movies).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }




    public record MovieUpdate(String oldMovie, String newMovie){}
}
