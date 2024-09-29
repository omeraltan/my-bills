package com.bills.boot.exception;

public class FaturaNotFoundException extends RuntimeException {
    public FaturaNotFoundException(String message) {
        super(message);
    }
}
