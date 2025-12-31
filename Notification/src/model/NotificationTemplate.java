package model;

public class NotificationTemplate {
    private long id;
    public ChannelType channelType;
    public String templateCode;      // “WELCOME_EMAIL”
    public String subjectTemplate;   // "Hi {{name}}"
    public String bodyTemplate;      // "Welcome to ABC {{name}}."

    public NotificationTemplate(long id, String templateCode, String subjectTemplate, String bodyTemplate) {
        this.id = id;
        this.templateCode = templateCode;
        this.subjectTemplate = subjectTemplate;
        this.bodyTemplate = bodyTemplate;
    }
}
