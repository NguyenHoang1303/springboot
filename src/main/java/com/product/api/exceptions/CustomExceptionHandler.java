package com.product.api.exceptions;

import com.product.api.responseApi.HandlerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HandlerResponse handlerNotFoundException(NotFoundException ex, WebRequest req) {
        return HandlerResponse.HandlerResponseBuilder.aHandlerResponse()
                .withStatus(HttpStatus.NO_CONTENT.value())
                .withMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HandlerResponse handlerValidationException(BindException ex) {
        List<FieldError> errors = ex.getFieldErrors();
        List<ValidationException> list = new ArrayList<>();
        for (FieldError e: errors) {
           list.add(ValidationException.ValidationExceptionBuilder.aValidationException()
                   .withField(e.getField())
                   .withDefaultMessage(e.getDefaultMessage())
                   .withObjectName(e.getObjectName())
                   .withRejectedValue(e.getRejectedValue())
                   .build());
        }
        return HandlerResponse.HandlerResponseBuilder.aHandlerResponse()
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withMessage(HttpStatus.BAD_REQUEST.name())
                .addData(HandlerResponse.ERRORS, list)
                .build();
    }

    @ExceptionHandler(SystemException.class)
    public HandlerResponse handlerSystemException(SystemException se) {
        return HandlerResponse.HandlerResponseBuilder.aHandlerResponse()
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .withMessage(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .build();
    }
}
