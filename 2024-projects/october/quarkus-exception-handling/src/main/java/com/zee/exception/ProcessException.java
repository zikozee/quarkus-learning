package com.zee.exception;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 30 Oct, 2024
 */

public class ProcessException extends RuntimeException {
    public ProcessException(String message) {
        super(message);
    }

    public ProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessException(Throwable cause) {
        super(cause);
    }
}
