package channel.validator;

import model.ChannelType;

public class NotificationValidatorFactory {
    public static NotificationValidator getNotificationValidator(ChannelType channelType) {
        switch (channelType) {
            case SMS -> new SMSValidator();
            case PUSH -> new PushValidator();
            case EMAIL -> new EmailValidator();
        }
        throw new IllegalArgumentException(channelType + " does not exist.");
    }
}