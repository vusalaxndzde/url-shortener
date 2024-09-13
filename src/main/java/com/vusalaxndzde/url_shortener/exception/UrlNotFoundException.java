package com.vusalaxndzde.url_shortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UrlNotFoundException extends RuntimeException
{

    public UrlNotFoundException(String message)
    {
        super(message);
    }

    public UrlNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public UrlNotFoundException()
    {
    }

}
