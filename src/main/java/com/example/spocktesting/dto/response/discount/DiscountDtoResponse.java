package com.example.spocktesting.dto.response.discount;

import lombok.Data;

@Data
public class DiscountDtoResponse {
    private Long id;
    private String discountName;
    private int percentage;
}
