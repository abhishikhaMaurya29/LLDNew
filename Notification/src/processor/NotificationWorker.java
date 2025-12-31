package processor;

import model.Notification;
import service.NotificationService;

public class NotificationWorker implements Runnable {
    private final NotificationService notificationService;
    private final Notification notification;

    public NotificationWorker(NotificationService notificationService, Notification notification) {
        this.notificationService = notificationService;
        this.notification = notification;
    }

    @Override
    public void run() {
        notificationService.process(notification);
    }
}