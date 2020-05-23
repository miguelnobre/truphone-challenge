package com.truphone.challenge.exception;

public class FamilyMemberNotFoundException extends NotFoundException {

    private static final String message = "Family Member not found!";

    public FamilyMemberNotFoundException() {
        super(message);
    }
}
