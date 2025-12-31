package channel.sender;

import model.ChannelConfig;
import model.Notification;

public class EmailSender implements NotificationSender {
    @Override
    public void sendNotification(Notification notification, ChannelConfig channelConfig) throws Exception {
        System.out.println("sending email to : " + notification.getRecipient());
        System.out.println("Using SMTP config: " + channelConfig.configJson);

        if (Math.random() < 0.3) throw new Exception("Email gateway is down.");
    }
}