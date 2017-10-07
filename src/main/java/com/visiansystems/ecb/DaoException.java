package com.visiansystems.ecb;

public class DaoException extends Exception {

    private String message;

    public DaoException(String message) {
        this.setMessage(message);
    }

    public DaoException() {
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
