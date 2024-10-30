package com.zee.exceptiondto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 30 Oct, 2024
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(String errorId, String message, List<ErrorMessage> errors){

}
