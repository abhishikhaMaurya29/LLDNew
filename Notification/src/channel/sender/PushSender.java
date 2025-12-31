package channel.sender;

import model.ChannelConfig;
import model.Notification;

public class PushSender implements NotificationSender {
    @Override
    public void sendNotification(Notification notification, ChannelConfig channelConfig) throws Exception {
        System.out.println("PUSH >> " + notification.getRecipient());
    }
}