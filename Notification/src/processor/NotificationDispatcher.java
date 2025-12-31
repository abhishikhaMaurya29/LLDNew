package processor;

import model.Notification;
import repository.InMemoryNotificationRepository;
import service.NotificationService;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class NotificationDispatcher {
    private final InMemoryNotificationRepository repo;
    private final NotificationService notificationService;
    private final ExecutorService executorService;

    public NotificationDispatcher(InMemoryNotificationRepository repo, NotificationService notificationService,
                                  ExecutorService executorService) {
        this.repo = repo;
        this.notificationService = notificationService;
        this.executorService = executorService;
    }

    public void start() {
        Thread pollingThread = new Thread(() -> {
            List<Notification> dueNotifications = repo.findDueNotifications(System.currentTimeMillis());
            for (Notification notification : dueNotifications) {
                executorService.submit(new NotificationWorker(notificationService, notification));
            }

            try {
                Thread.sleep(1000);
            } catch (Exception ignored) {

            }
        });

        pollingThread.setDaemon(true);
        pollingThread.start();
    }
}