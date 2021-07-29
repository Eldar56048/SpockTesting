package com.example.spocktesting.dto.response.client;

import com.example.spocktesting.dto.response.discount.DiscountDtoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDtoResponse {
    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private DiscountDtoResponse discount;
}
