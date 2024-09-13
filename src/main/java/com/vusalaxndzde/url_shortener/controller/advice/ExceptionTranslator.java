package com.vusalaxndzde.url_shortener.controller.advice;

import com.vusalaxndzde.url_shortener.exception.UrlNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionTranslator
{

    @ExceptionHandler(value = UrlNotFoundException.class)
    public ResponseEntity<Void> handleUrlNotFoundException(UrlNotFoundException ex)
    {
        return ResponseEntity.notFound().build();
    }

}
