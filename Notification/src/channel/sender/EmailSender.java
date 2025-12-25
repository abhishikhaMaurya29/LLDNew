package channel.sender;

import model.Notification;

public class EmailSender implements NotificationSender {
    @Override
    public void sendNotification(Notification notification) throws Exception {
        System.out.println("sending email to : " + notification.getRecipient());
        if (Math.random() < 0.3) throw new Exception("Email gateway is down.");
    }
}