package com.mike.healthmadeeasy.exception;

public class DuplicateFoodException extends RuntimeException {
    public DuplicateFoodException(String message) {
        super(message);
    }
}
