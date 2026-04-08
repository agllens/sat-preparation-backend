package com.sat_preparation_backend.exception;


public class SubscriptionRequiredException extends RuntimeException {
    public SubscriptionRequiredException() {
        super("Subscription required");
    }
}