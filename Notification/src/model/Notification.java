package model;

import java.util.UUID;

public class Notification {
    private final UUID id;
    public final String tenantId;
    private final String recipient;
    private final ChannelType channelType;
    private NotificationStatus notificationStatus = NotificationStatus.IN_PROGRESS;
    private int attemptCount = 0;
    private final int maxAttemptsCount = 3;
    private String subject;
    private String body;
    private String lastError;
    private long nextTimeStamp;

    public Notification(String recipient, ChannelType type, String tenantId) {
        this.id = UUID.randomUUID();
        this.nextTimeStamp = System.currentTimeMillis();
        this.recipient = recipient;
        this.channelType = type;
        this.tenantId = tenantId;
    }

    public String getRecipient() {
        return recipient;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public UUID getId() {
        return id;
    }

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public long getNextTimeStamp() {
        return nextTimeStamp;
    }

    public int getMaxAttemptsCount() {
        return maxAttemptsCount;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public void setLastError(String lastError) {
        this.lastError = lastError;
    }

    public void setAttemptCount(int attemptCount) {
        this.attemptCount = attemptCount;
    }

    public void setNextTimeStamp(long nextTimeStamp) {
        this.nextTimeStamp = nextTimeStamp;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }
}