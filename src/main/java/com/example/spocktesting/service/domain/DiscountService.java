package com.example.spocktesting.service.domain;

import com.example.spocktesting.dto.request.discount.DiscountCreateDtoRequest;
import com.example.spocktesting.dto.request.discount.DiscountUpdateDtoRequest;
import com.example.spocktesting.model.Discount;
import com.example.spocktesting.service.BaseService;

public interface DiscountService extends BaseService<Discount> {
    Discount create(DiscountCreateDtoRequest dto);
    Discount update(DiscountUpdateDtoRequest dto);
    boolean existsByDiscountName(String discountName);
    boolean existsByDiscountNameAndIdNotLike(String discountName, Long id);
    boolean existsByPercentage(int percentage);
    boolean existsByPercentageAndIdNotLike(int percentage, Long id);
}
