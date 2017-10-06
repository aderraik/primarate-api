package com.visiansystems.exception;

public class BankRateFeedException extends Exception {

    private static final long serialVersionUID = 7799769560868923458L;

    private String message;

    public BankRateFeedException(String message) {
        this.setMessage(message);
    }

    public BankRateFeedException() {
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
