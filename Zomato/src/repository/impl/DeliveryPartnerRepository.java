package repository.impl;

import model.DeliveryPartner;

import java.util.List;

public interface DeliveryPartnerRepository {
    List<DeliveryPartner> findAvailablePartners();
}