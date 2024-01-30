package com.ikemole.expressionevaluator.math;

/**
 * The exception for invalid fractions.
 */
public final class InvalidFractionException extends RuntimeException{
    private final FractionError errorType;

    public InvalidFractionException(FractionError errorType) {
        super(errorType.toString());
        this.errorType = errorType;
    }

    public FractionError getErrorType() {
        return errorType;
    }
}
