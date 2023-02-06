package ru.otus.domain;

public enum VerificationStatus {
    IN_PROGRESS("In Progress"),
    REJECTED("Rejected"),

    VERIFIED("Verified");

    private final String status;

    VerificationStatus(String status) {
        this.status = status;
    }


}
