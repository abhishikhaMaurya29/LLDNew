package service;

import model.Notification;

public class ExponentialBackoffRetryPolicy implements RetryPolicy {
    @Override
    public boolean shouldRetry(Notification notification) {
        return notification.getAttemptCount() < notification.getMaxAttemptsCount();
    }

    @Override
    public long computeNextAttempt(Notification notification) {
        long delay = (long) Math.pow(2, notification.getAttemptCount()) * 1000;
        return System.currentTimeMillis() + delay;
    }
}
