package service;

import model.Notification;

public interface RetryPolicy {
    boolean shouldRetry(Notification notification);

    long computeNextAttempt(Notification notification);
}
