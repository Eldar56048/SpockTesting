package com.example.spocktesting.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
public class ValidationErrorDetails {
    private ErrorDetails errorDetails;
    // not the best naming. It's not clear what the purpose of this map is
    private Map<String, String> validation;
}

