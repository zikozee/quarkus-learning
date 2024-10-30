package com.zee.exceptionhandler;

import com.zee.exception.FieldValidationException;
import com.zee.exceptiondto.ErrorResponse;
import com.zee.util.ExceptionUtil;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 30 Oct, 2024
 */

@Provider
@Slf4j
public class FieldValidationExceptionHandler implements ExceptionMapper<FieldValidationException> {

    @Override
    public Response toResponse(FieldValidationException exception) {
        String errorId = ExceptionUtil.generateUUID();
        log.error(errorId);
        ErrorResponse errorResponse = new ErrorResponse(errorId, null, exception.getFieldErrors());

        return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
    }
}
