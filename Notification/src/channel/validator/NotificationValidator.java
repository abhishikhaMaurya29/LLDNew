package channel.validator;

import model.SendNotificationRequest;

public interface NotificationValidator {
    void validate(SendNotificationRequest sendNotificationRequest);
}
