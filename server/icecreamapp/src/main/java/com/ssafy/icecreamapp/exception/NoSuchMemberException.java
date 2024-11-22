package com.ssafy.icecreamapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoSuchMemberException extends RuntimeException{
    public NoSuchMemberException(String message){
        super(message);
    }

}
