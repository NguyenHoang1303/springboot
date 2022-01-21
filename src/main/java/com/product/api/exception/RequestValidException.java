package com.product.api.exception;

public class RequestValidException extends RuntimeException {
    public RequestValidException(String message) {
        super(message);
    }
}
