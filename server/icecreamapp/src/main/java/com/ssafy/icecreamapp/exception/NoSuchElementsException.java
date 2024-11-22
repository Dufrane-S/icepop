package com.ssafy.icecreamapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoSuchElementsException extends RuntimeException{
    public NoSuchElementsException(String message){
        super(message);
    }

}
