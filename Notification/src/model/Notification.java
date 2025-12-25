package model;

import java.util.UUID;

public class Notification {
    private final UUID id;
    private final String recipient;
    private final ChannelType channelType;
    private NotificationStatus notificationStatus = NotificationStatus.IN_PROGRESS;
    private int attemptCount = 0;
    private int maxAttemptsCount = 3;
    private String subject;
    private String body;
    private String lastError;
    private long nextTimeStamp;

    public Notification(String recipient, ChannelType type, String subject, String body) {
        this.id = UUID.randomUUID();
        this.nextTimeStamp = System.currentTimeMillis();
        this.recipient = recipient;
        this.channelType = type;
        this.subject = subject;
        this.body = body;
    }

    public String getRecipient() {
        return recipient;
    }
}