package com.example.spocktesting.constant;

// 1. This is utility class. Always try to prohibit instance creation for this kind of classes
// 2. Method name convention is violated in this class. It should be camelCase in all cases
public class ResponseCodeMessage {
    // Note for future: We never use local language for any domain as programmers.
    // Error messages, comments and other info should be written in english only
    // Exception: Error messages and texts that are being shown in UI can be localized in different languages using special locale mechanism
    public static final String DISCOUNT_EXISTS_BY_NAME_MESSAGE = "Скидка с таким названием уже существует";
    public static final String DISCOUNT_EXISTS_BY_PERCENT_MESSAGE = "Скидка с таким процентом уже существует";
    // Misleading constant name. It's not clear what it's about without seeing the value. Hint: TWO_DIFFERENT_IDS
    public static final String CLIENT_TWO_ANOTHER_ID_MESSAGE = "Два разных id";
    public static final String CANNOT_DELETE_ALREADY_IN_USE_MESSAGE = "Невозможно удалить так как он уже используется";
    public static final String VALIDATION_FAILED_MESSAGE = "Валидация провалена";

    // The framework should handle this kind of error by default
    public static String METHOD_NOT_ALLOWED_MESSAGE(String method) {
        return "Метод запроса "+method+" не поддерживается";
    }

    // You could
    public static String DISCOUNT_NOT_FOUND_MESSAGE(Long discountId) {
        return "Скидка с идентификатором № "+discountId+" не найдено";
    }

    public static String CLIENT_NOT_FOUND_MESSAGE(Long clientId) {
        return "Клиент с идентификатором № "+clientId+" не найдено";
    }
}
