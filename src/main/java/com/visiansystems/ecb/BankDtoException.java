package com.visiansystems.ecb;

/**
 * This exception's class represents all the issues related to the interface
 * between our service and the remote one. Including connection issues, timeout
 * and error responses.
 */
public class BankDtoException extends Exception {

    private String message;

    public BankDtoException(String message) {
        this.setMessage(message);
    }

    public BankDtoException() {
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
