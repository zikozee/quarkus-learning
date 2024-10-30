package com.zee.exception;

import com.zee.exceptiondto.ErrorMessage;
import lombok.Getter;

import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 30 Oct, 2024
 */

@Getter
public class FieldValidationException extends RuntimeException {
    private final List<ErrorMessage> fieldErrors;

    public FieldValidationException(List<ErrorMessage> fieldErrors) {
        super("");
        this.fieldErrors = fieldErrors;
    }
}
