package com.saurabh.ecommerce.product_service.advice;

import com.saurabh.ecommerce.product_service.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String, String> HandleBusinessException(UserNotFoundException ex) {
        System.out.println("Saurabh Error");
        Map<String, String> err = new HashMap<>();
        err.put("error Message", ex.getMessage());
        return err;
    }
}
