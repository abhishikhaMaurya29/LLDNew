package channel.sender;

import model.ChannelType;

public class NotificationSenderFactory {
    public NotificationSender get(ChannelType channelType) {
        switch (channelType) {
            case EMAIL -> new EmailSender();
            case SMS -> new SMSSender();
            case PUSH -> new PushSender();
        }
        throw new IllegalArgumentException("Unknown channel");
    }
}
