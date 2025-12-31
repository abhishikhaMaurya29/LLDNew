package model;

public class SendNotificationRequest {
    public String recipient;
    public ChannelType channelType;
    public String subject;
    public String body;

    public SendNotificationRequest(String recipient, ChannelType channelType, String subject, String body) {
        this.recipient = recipient;
        this.channelType = channelType;
        this.subject = subject;
        this.body = body;
    }
}