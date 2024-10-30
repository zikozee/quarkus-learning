package com.zee.exceptionhandler;

import com.zee.exceptiondto.ErrorResponse;
import com.zee.util.ExceptionUtil;
import jakarta.ws.rs.ClientErrorException;
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
public class ClientExceptionHandler implements ExceptionMapper<ClientErrorException> {

    @Override
    public Response toResponse(ClientErrorException exception) {
        String errorId = ExceptionUtil.generateUUID();
        log.error(errorId);
        ErrorResponse errorResponse = new ErrorResponse(errorId, exception.getLocalizedMessage(), null);

        return Response.status(exception.getResponse().getStatus()).entity(errorResponse).build();
    }
}
