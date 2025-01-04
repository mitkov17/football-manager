package com.mitkov.footballmanager.exceptions;

public class InsufficientBudgetException extends RuntimeException {

    public InsufficientBudgetException(String message) {
        super(message);
    }
}
