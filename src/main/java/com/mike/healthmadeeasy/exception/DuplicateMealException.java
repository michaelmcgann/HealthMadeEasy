package com.mike.healthmadeeasy.exception;

public class DuplicateMealException extends RuntimeException {

    public DuplicateMealException(String name) {
        super(name);
    }

}
