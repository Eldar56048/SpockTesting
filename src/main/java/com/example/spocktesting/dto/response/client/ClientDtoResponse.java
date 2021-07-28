package com.example.spocktesting.dto.response.client;

import com.example.spocktesting.dto.response.discount.DiscountDtoResponse;
import lombok.Data;

@Data
public class ClientDtoResponse {
    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private DiscountDtoResponse discount;
}
