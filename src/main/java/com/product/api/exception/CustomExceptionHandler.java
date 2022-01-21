package com.product.api.exception;

import com.product.api.responseApi.HandlerResponse;
import com.product.api.responseApi.RESTResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity handlerNotFoundException(NotFoundException ex, WebRequest req) {
        return new ResponseEntity<>(new RESTResponse.SimpleError()
                .setCode(HttpStatus.NOT_FOUND.value())
                .setMessage(ex.getMessage())
                .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handlerRequestValidException(RequestValidException ex, WebRequest req) {
        return new ResponseEntity<>(new RESTResponse.SimpleError()
                .setCode(HttpStatus.BAD_REQUEST.value())
                .setMessage(ex.getMessage())
                .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationCustom.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity handlerAuthenticationCustomException(AuthenticationCustom au, WebRequest req) {
        return new ResponseEntity<>(new RESTResponse.SimpleError()
                .setCode(HttpStatus.UNAUTHORIZED.value())
                .setMessage(au.getMessage())
                .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HandlerResponse handlerValidationException(BindException ex) {
        List<FieldError> errors = ex.getFieldErrors();
        List<ValidationException> list = new ArrayList<>();
        for (FieldError e : errors) {
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
