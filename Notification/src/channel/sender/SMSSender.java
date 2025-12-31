package channel.sender;

import model.ChannelConfig;
import model.Notification;

public class SMSSender implements NotificationSender {
    @Override
    public void sendNotification(Notification notification, ChannelConfig channelConfig) throws Exception {
        System.out.println("SMS >> " + notification.getRecipient());
        if (Math.random() < 0.2) throw new Exception("SMS Gateway Down");
    }
}
