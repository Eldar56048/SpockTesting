package com.example.spocktesting.exception.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
// Formatting is crucial to the class files. Learn how it's properly done in Java convention
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DtoException extends RuntimeException{
    // usually you are good sticking to the default value of UID. If not, then make it different for all classes
    private static final long serialVersionUID = 1L;
    private String code;
    public DtoException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
