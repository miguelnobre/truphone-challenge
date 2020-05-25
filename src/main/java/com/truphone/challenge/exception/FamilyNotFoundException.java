package com.truphone.challenge.exception;

public class FamilyNotFoundException extends NotFoundException {

    private static final String message = "Family not found!";

    public FamilyNotFoundException() {
        super(message);
    }
}
