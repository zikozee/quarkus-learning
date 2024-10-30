package com.zee.util;

import com.zee.exceptiondto.ErrorMessage;
import jakarta.validation.Validation;

import java.util.List;
import java.util.UUID;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 30 Oct, 2024
 */

public final class ExceptionUtil {

    private ExceptionUtil() {
        throw new RuntimeException("Cannot be instantiated");
    }

    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }

    public static <T> List<ErrorMessage> validationErrors(T data){
        return Validation.buildDefaultValidatorFactory().getValidator()
                .validate(data)
                .parallelStream()
                .map(constraint -> new ErrorMessage(constraint.getPropertyPath().toString(), constraint.getMessage()))
                .toList();
    }
}
