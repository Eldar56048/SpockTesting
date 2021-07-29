package com.example.spocktesting.dto.response.discount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDtoResponse {
    private Long id;
    private String discountName;
    private int percentage;
}
