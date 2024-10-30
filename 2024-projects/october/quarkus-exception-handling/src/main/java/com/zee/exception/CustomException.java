package com.zee.exception;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 30 Oct, 2024
 */

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }
}
