package com.truphone.challenge.exception;

public class SpouseNotFoundException extends NotFoundException {

    private static final String message = "Spouse not found!";

    public SpouseNotFoundException() {
        super(message);
    }
}
