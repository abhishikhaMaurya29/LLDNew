package delivery;

import model.DeliveryPartner;
import model.Order.OrderContext;
import repository.impl.DeliveryPartnerRepository;

public interface DeliveryAssignmentStrategy {
    DeliveryPartner assign(OrderContext order, DeliveryPartnerRepository deliveryPartnerRepository);
}
