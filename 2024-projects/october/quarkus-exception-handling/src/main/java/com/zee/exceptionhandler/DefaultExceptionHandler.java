package com.zee.exceptionhandler;

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
public class DefaultExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable throwable) {
        String errorId = ExceptionUtil.generateUUID();
        log.error(errorId);
        ErrorResponse errorResponse = new ErrorResponse(errorId, throwable.getLocalizedMessage(), null);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
    }
}
