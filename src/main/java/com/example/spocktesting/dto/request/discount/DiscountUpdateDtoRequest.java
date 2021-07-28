package com.example.spocktesting.dto.request.discount;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
public class DiscountUpdateDtoRequest {
    @NotNull(message = "Поле id обязательно")
    @Positive(message = "id не может быть негативным числом")
    private Long id;
    @NotBlank(message = "Поле название скидки обязательно.")
    @Length(message = "Длина название скидки должна быть больше нуля", min = 1)
    private String discountName;
    @NotNull(message = "Поле процент обязательно.")
    @Min(value = 0,message = "Значение поле должно быть больше 0")
    @Max(value = 100, message = "Значение поле должно быть меньше 100")
    private int percentage;
}
