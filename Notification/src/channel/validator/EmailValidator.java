package channel.validator;

import model.SendNotificationRequest;

public class EmailValidator implements NotificationValidator {
    @Override
    public void validate(SendNotificationRequest sendNotificationRequest) {
        if (sendNotificationRequest.recipient == null || !sendNotificationRequest.recipient.contains("@")) {
            throw new IllegalArgumentException("Email is not valid");
        }

        if (sendNotificationRequest.body == null || sendNotificationRequest.subject == null) {
            throw new IllegalArgumentException("subject and body must be provided");
        }
    }
}
