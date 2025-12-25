package model;

public class NotificationTemplate {
    private long id;
    private String templateCode;
    private String subjectTemplate;
    private String bodyTemplate;

    public NotificationTemplate(long id, String templateCode, String subjectTemplate, String bodyTemplate) {
        this.id = id;
        this.templateCode = templateCode;
        this.subjectTemplate = subjectTemplate;
        this.bodyTemplate = bodyTemplate;
    }
}
