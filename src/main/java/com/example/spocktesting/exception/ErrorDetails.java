package com.example.spocktesting.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorDetails {
    private int status;
    private Date timeStamp;
    private String message;
    private String details;
    private String code;
}
