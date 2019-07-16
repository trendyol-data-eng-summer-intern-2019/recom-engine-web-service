package com.trendyol.recomengine.resource;

public class ValidationError {

    private String errorMessage;

    private ValidationError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static ValidationError generateInvalidScoreError() {
        return new ValidationError(ValidationErrorTypes.INVALID_SCORE_ERROR);
    }

    public static ValidationError generateInvalidTimestampError() {
        return new ValidationError(ValidationErrorTypes.INVALID_TIMESTAMP_ERROR);
    }

    public static ValidationError generateInvalidUserIdError() {
        return new ValidationError(ValidationErrorTypes.INVALID_USER_ID);
    }

    public static ValidationError generateInvalidProductIdError() {
        return new ValidationError(ValidationErrorTypes.INVALID_PRODUCT_ID);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private class ValidationErrorTypes {
        final static String INVALID_SCORE_ERROR = "Score must be in range [0 - 5].";
        final static String INVALID_TIMESTAMP_ERROR = "Timestamp must be greater than 0.";
        final static String INVALID_USER_ID = "User id must contain only alphanumeric characters.";
        final static String INVALID_PRODUCT_ID = "Product id must contain only alphanumeric characters.";
    }
}
