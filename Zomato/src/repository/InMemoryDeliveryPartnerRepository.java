package repository;

import model.DeliveryPartner;
import repository.impl.DeliveryPartnerRepository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryDeliveryPartnerRepository implements DeliveryPartnerRepository {
    private final List<DeliveryPartner> partners = new CopyOnWriteArrayList<>();

    @Override
    public List<DeliveryPartner> findAvailablePartners() {
        return partners.stream().filter(DeliveryPartner::isAvailable).toList();
    }

    public void addPartner(DeliveryPartner deliveryPartner) {
        partners.add(deliveryPartner);
    }
}