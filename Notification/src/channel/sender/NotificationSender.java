package channel.sender;

import model.Notification;

public interface NotificationSender {
    void sendNotification(Notification notification) throws Exception;
}
