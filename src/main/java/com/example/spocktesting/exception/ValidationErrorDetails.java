package com.example.spocktesting.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
public class ValidationErrorDetails {
    private ErrorDetails errorDetails;
    private Map<String, String> validation;
}

