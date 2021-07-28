package com.example.spocktesting.constant;

public class ResponseCodeMessage {
    public static final String DISCOUNT_EXISTS_BY_NAME_MESSAGE = "Скидка с таким названием уже существует";
    public static final String DISCOUNT_EXISTS_BY_PERCENT_MESSAGE = "Скидка с таким процентом уже существует";
    public static final String CLIENT_TWO_ANOTHER_ID_MESSAGE = "Два разных id";
    public static final String CANNOT_DELETE_ALREADY_IN_USE_MESSAGE = "Невозможно удалить так как он уже используется";
    public static final String VALIDATION_FAILED_MESSAGE = "Валидация провалена";

    public static String METHOD_NOT_ALLOWED_MESSAGE(String method) {
        return "Метод запроса "+method+" не поддерживается";
    }

    public static String DISCOUNT_NOT_FOUND_MESSAGE(Long discountId) {
        return "Скидка с идентификатором № "+discountId+" не найдено";
    }

    public static String CLIENT_NOT_FOUND_MESSAGE(Long clientId) {
        return "Клиент с идентификатором № "+clientId+" не найдено";
    }
}
