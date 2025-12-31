package service;

import channel.sender.NotificationSender;
import channel.sender.NotificationSenderFactory;
import model.ChannelConfig;
import model.Notification;
import model.NotificationStatus;
import model.NotificationTemplate;
import repository.InMemoryChannelConfigRepository;
import repository.InMemoryNotificationRepository;
import repository.InMemoryTemplateRepository;

import java.util.UUID;

public class NotificationService {
    private final InMemoryNotificationRepository inMemoryNotificationRepository;
    private final InMemoryChannelConfigRepository inMemoryChannelConfigRepository;
    private final InMemoryTemplateRepository templateRepo;
    private final NotificationSenderFactory notificationSenderFactory;
    private final RetryPolicy retryPolicy;

    public NotificationService(InMemoryNotificationRepository inMemoryNotificationRepository,
                               NotificationSenderFactory notificationSenderFactory,
                               RetryPolicy retryPolicy,
                               InMemoryChannelConfigRepository inMemoryChannelConfigRepository,
                               InMemoryTemplateRepository templateRepo) {
        this.inMemoryNotificationRepository = inMemoryNotificationRepository;
        this.notificationSenderFactory = notificationSenderFactory;
        this.retryPolicy = retryPolicy;
        this.inMemoryChannelConfigRepository = inMemoryChannelConfigRepository;
        this.templateRepo = templateRepo;
    }

    public UUID create(Notification notification, String templateCode) {
        NotificationTemplate notificationTemplate = templateRepo.find(templateCode, notification.getChannelType());

        if (notificationTemplate == null) {
            throw new IllegalArgumentException("Template not found");
        }

        notification.setBody(notificationTemplate.bodyTemplate);
        notification.setSubject(notificationTemplate.subjectTemplate);

        inMemoryNotificationRepository.save(notification);
        return notification.getId();
    }

    public Notification getById(UUID uuid) {
        return inMemoryNotificationRepository.findById(uuid);
    }

    public void process(Notification notification) {
        NotificationSender notificationSender = notificationSenderFactory.get(notification.getChannelType());
        ChannelConfig channelConfig = inMemoryChannelConfigRepository.get(notification.tenantId, notification.getChannelType());

        try {
            notification.setNotificationStatus(NotificationStatus.IN_PROGRESS);
            inMemoryNotificationRepository.save(notification);

            notificationSender.sendNotification(notification, channelConfig);

            notification.setNotificationStatus(NotificationStatus.SUCCESS);
            int cnt = notification.getAttemptCount();
            notification.setAttemptCount(++cnt);

            inMemoryNotificationRepository.save(notification);
        } catch (Exception e) {
            int cnt = notification.getAttemptCount();

            notification.setAttemptCount(++cnt);
            notification.setLastError(e.getMessage());

            if (retryPolicy.shouldRetry(notification)) {
                notification.setNotificationStatus(NotificationStatus.FAILED);
                notification.setNextTimeStamp(retryPolicy.computeNextAttempt(notification));
            } else {
                notification.setNotificationStatus(NotificationStatus.PERMANENT_FAILURE);
            }

            inMemoryNotificationRepository.save(notification);
        }
    }
}