package com.example.spocktesting.constant;

// This is utility class. Always try to prohibit instance creation for this kind of classes
public class ResponseCode {
    public static final String DISCOUNT_EXISTS_BY_NAME = "discount/exists-by-name";
    public static final String DISCOUNT_EXISTS_BY_PERCENT = "discount/exists-by-percent";
    public static final String DISCOUNT_SUCCESSFULLY_DELETED = "discount/successfully-deleted";
    public static final String DISCOUNT_TWO_ANOTHER_ID = "discount/two-another-id";
    public static final String DISCOUNT_NOT_FOUND = "discount/not-found";
    public static final String CLIENT_NOT_FOUND = "client/not-found";
    public static final String CLIENT_TWO_ANOTHER_ID = "client/two-another-id";
    public static final String CLIENT_SUCCESSFULLY_DELETED = "client/successfully-deleted";
    public static final String DATA_NOT_DELETED = "data/not-delete";
    public static final String VALIDATION_FAILED = "validation/failed";
    public static final String METHOD_NOT_ALLOWED = "method/not-allowed";
}
