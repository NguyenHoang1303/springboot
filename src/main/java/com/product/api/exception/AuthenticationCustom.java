package com.product.api.exception;

public class AuthenticationCustom extends RuntimeException {
    AuthenticationCustom(String message) {
        super(message);
    }
}
