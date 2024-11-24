package com.ssafy.icecreamapp.exception;

import java.util.NoSuchElementException;

public class MyNoSuchElementException extends NoSuchElementException {
    public MyNoSuchElementException(String parameterType, String parameter) {
        super("없는 " + parameterType + " : " + parameter);
    }
}
