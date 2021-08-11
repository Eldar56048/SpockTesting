package com.example.spocktesting.dto.request.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Again russian error messages exposed to the user on UI
public class ClientCreateDtoRequest {
    @NotBlank(message = "Полe Имя обязательно.")
    @Length(message = "Длина Имени должна быть больше нуля", min = 1)
    private String name;
    @NotBlank(message = "Поле Фамилия обязательно.")
    @Length(message = "Длина Фамилии должна быть больше нуля", min = 1)
    private String surname;
    @NotBlank(message = "Телефон номер обязательно.")
    @Pattern(regexp = "[8][0-9]{10}", message = "Номер телефон не соответствует формату")
    private String phoneNumber;
    @NotNull(message = "Поле id обязательно")
    @Positive(message = "id не может быть негативным числом")
    private Long discountId;
}
