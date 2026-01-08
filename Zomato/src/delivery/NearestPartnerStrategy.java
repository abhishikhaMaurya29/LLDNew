package delivery;

import model.DeliveryPartner;
import model.Order.OrderContext;
import repository.impl.DeliveryPartnerRepository;

public class NearestPartnerStrategy implements DeliveryAssignmentStrategy {

    @Override
    public DeliveryPartner assign(OrderContext orderContext, DeliveryPartnerRepository deliveryPartnerRepository) {
        for (DeliveryPartner deliveryPartner : deliveryPartnerRepository.findAvailablePartners()) {
            if (deliveryPartner.markBusy()) {
                return deliveryPartner;
            }
        }

        throw new IllegalStateException("No delivery partner available.");
    }
}