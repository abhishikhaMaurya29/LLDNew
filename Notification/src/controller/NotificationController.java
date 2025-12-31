package controller;

import channel.validator.NotificationValidator;
import channel.validator.NotificationValidatorFactory;
import model.ChannelType;
import model.Notification;
import model.SendNotificationRequest;
import service.NotificationService;

import java.util.UUID;

public class NotificationController {
    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    public UUID send(SendNotificationRequest req, String tenantId, String templateCode) {
        NotificationValidator notificationValidator = NotificationValidatorFactory
                .getNotificationValidator(req.channelType);

        notificationValidator.validate(req);
        Notification n = new Notification("ab@gmail.com", ChannelType.EMAIL, tenantId);
        return service.create(n, templateCode);
    }

    public Notification getStatus(UUID id) {
        return service.getById(id);
    }
}
