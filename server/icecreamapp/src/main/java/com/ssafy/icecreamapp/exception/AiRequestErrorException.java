package com.ssafy.icecreamapp.exception;

public class AiRequestErrorException extends RuntimeException {
    public AiRequestErrorException(String message) {
        super(message);
    }
}
