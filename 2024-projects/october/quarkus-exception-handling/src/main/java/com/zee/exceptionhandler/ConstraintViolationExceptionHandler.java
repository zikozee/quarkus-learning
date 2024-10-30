package com.zee.exceptionhandler;

import com.zee.exceptiondto.ErrorMessage;
import com.zee.exceptiondto.ErrorResponse;
import com.zee.util.ExceptionUtil;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 30 Oct, 2024
 */


@Provider
@Slf4j
public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        String errorId = ExceptionUtil.generateUUID();
        log.error(errorId);

        List<ErrorMessage> errorMessageList = exception.getConstraintViolations()
                .parallelStream()
                .map(constraintViolation -> new ErrorMessage(
                        constraintViolation.getPropertyPath().toString().substring(
                                constraintViolation.getPropertyPath().toString().lastIndexOf(".") + 1
                        ),
                        constraintViolation.getMessage()))
                .toList();
        ErrorResponse errorResponse = new ErrorResponse(errorId, null, errorMessageList);

        return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
    }
}
