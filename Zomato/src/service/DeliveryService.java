package service;

import delivery.DeliveryAssignmentStrategy;
import model.DeliveryPartner;
import model.Order.OrderContext;
import repository.impl.DeliveryPartnerRepository;
import retry.RetryConfigs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class DeliveryService {
    private final DeliveryAssignmentStrategy deliveryAssignmentStrategy;
    private final DeliveryPartnerRepository deliveryPartnerRepository;

    public DeliveryService(DeliveryAssignmentStrategy deliveryAssignmentStrategy, DeliveryPartnerRepository deliveryPartnerRepository) {
        this.deliveryAssignmentStrategy = deliveryAssignmentStrategy;
        this.deliveryPartnerRepository = deliveryPartnerRepository;
    }

    public void assignDeliveryPartner(OrderContext order) {
        DeliveryPartner partner = deliveryAssignmentStrategy.assign(order, deliveryPartnerRepository);
        ReentrantLock lock = PartnerLockRegistry.getLock(partner.getId());

        boolean acquired = RetryConfigs.DELIVERY_RETRY.executeWithResult(() -> {
            try {
                return lock.tryLock(3, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        });

        if (!acquired) {
            throw new IllegalStateException("Partner busy: " + partner.getId());
        }

        try {
            System.out.println("Assigned partner " + partner.getId() + " to order " + order.getOrderId());
        } finally {
            lock.unlock();
        }
    }
}