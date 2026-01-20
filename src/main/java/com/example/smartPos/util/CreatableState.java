package com.example.smartPos.util;

public enum CreatableState {
    ACTIVE(1),
    REJECTED(2),
    MODIFIED(3),
    PENDING(5),
    REQUESTED(6),
    INACTIVE(9),
    FINISHED(7),
    EXPIRED(8),
    SUSPENDED(90),
    CANCELLED(91),
    FAILED(98),
    DELETED(99);
    public final int digit;

    CreatableState(int digit) {
        this.digit = digit;
    }
}
