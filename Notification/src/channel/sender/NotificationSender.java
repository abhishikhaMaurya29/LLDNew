package channel.sender;

import model.ChannelConfig;
import model.Notification;

public interface NotificationSender {
    void sendNotification(Notification notification, ChannelConfig channelConfig) throws Exception;
}
