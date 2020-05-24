package com.truphone.challenge.exception;

public class SpouseAlreadyMarriedException extends IllegalArgumentException {

    private static final String message = "Spouse is already in another Marriage!";

    public SpouseAlreadyMarriedException() {
        super(message);
    }
}
