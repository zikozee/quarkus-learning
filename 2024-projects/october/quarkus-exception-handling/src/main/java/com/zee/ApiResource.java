package com.zee;

import com.zee.exception.CustomException;
import com.zee.exception.FieldValidationException;
import com.zee.exceptiondto.ErrorMessage;
import com.zee.util.ExceptionUtil;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/exception-handling")
public class ApiResource {

    @POST
    @Path("constraint")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response constraintError(@Valid Customer customer) {
        return Response.ok().entity(customer).build();
    }

    @POST
    @Path("constraint-validator")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response constraintValidatorError(Customer customer) {
        List<ErrorMessage> errorMessages = ExceptionUtil.validationErrors(customer);
        if (!errorMessages.isEmpty()) throw new FieldValidationException(errorMessages);

        return Response.ok().entity(customer).build();
    }

    @GET
    @Path("not-found")
    @Produces(MediaType.APPLICATION_JSON)
    public Response notFound() {
        throw new NotFoundException("resource not found");
    }

    @GET
    @Path("client-error")
    @Produces(MediaType.APPLICATION_JSON)
    public Response clientError() {
        throw new ClientErrorException("client error", Response.Status.BAD_REQUEST);
    }

    @GET
    @Path("server-error")
    @Produces(MediaType.APPLICATION_JSON)
    public Response serverError() {
        throw new ServerErrorException("server error", Response.Status.SERVICE_UNAVAILABLE);
    }

    @GET
    @Path("custom-error")
    @Produces(MediaType.APPLICATION_JSON)
    public Response customError() {
        throw new CustomException("custom error");
    }

    @GET
    @Path("process-error")
    @Produces(MediaType.APPLICATION_JSON)
    public Response processError() {
        throw new ProcessingException("process error");
    }

    @GET
    @Path("generic-error-1")
    @Produces(MediaType.APPLICATION_JSON)
    public Response genericError() {
        throw new RuntimeException("some generic error");
    }
    @GET
    @Path("generic-error-2")
    @Produces(MediaType.APPLICATION_JSON)
    public Response genericError2() {
        try{
            throw new Exception("generic error 2");
        }catch(Exception e){
            throw new ServerErrorException(e.getLocalizedMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
