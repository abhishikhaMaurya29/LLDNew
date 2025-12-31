package repository;

import model.Notification;
import model.NotificationStatus;

import java.util.*;

public class InMemoryNotificationRepository {
    private final Map<UUID, Notification> store = new HashMap<>();

    public synchronized void save(Notification notification) {
        store.put(notification.getId(), notification);
    }

    public synchronized Notification findById(UUID uuid) {
        return store.get(uuid);
    }

    public synchronized List<Notification> findDueNotifications(Long now) {
        List<Notification> dueNotifications = new ArrayList<>();

        for (Notification notification : store.values()) {
            if (notification.getNotificationStatus().equals(NotificationStatus.PENDING) || notification.getNotificationStatus().equals(NotificationStatus.FAILED)
                    && notification.getNextTimeStamp() <= now) {
                dueNotifications.add(notification);
            }
        }

        return dueNotifications;
    }
}
