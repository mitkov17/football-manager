package com.mitkov.footballmanager.exceptions;

public class InsufficientBudgetException extends RuntimeException {

    public InsufficientBudgetException() {
        super("Target team does not have enough budget for this transfer.");
    }
}
