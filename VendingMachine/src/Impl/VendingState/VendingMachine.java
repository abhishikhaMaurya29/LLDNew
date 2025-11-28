package Impl.VendingState;

import Impl.Inventory.InventoryRepository;
import Impl.PaymentStrategy.CashPaymentStrategy;
import Impl.PaymentStrategy.PaymentStrategy;
import Impl.PaymentStrategy.PaymentStrategyFactory;
import Impl.PaymentStrategy.PaymentType;
import Impl.PricingStrategy.PricingStrategy;
import Model.InventorySlot;
import Model.Money;

import java.util.Currency;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class VendingMachine {
    private VendingState vendingState;
    private final PaymentStrategy paymentStrategy;
    private final InventoryRepository inventoryRepository;
    private String selectedProductCode;
    private final Lock lock = new ReentrantLock();
    private final PricingStrategy pricingStrategy;

    public VendingMachine(InventoryRepository inMemoryInventoryRepository, PricingStrategy pricingStrategy, PaymentType paymentType, Currency currency) {
        this.vendingState = new IdleState();
        this.paymentStrategy = PaymentStrategyFactory.create(paymentType, currency);
        this.inventoryRepository = inMemoryInventoryRepository;
        this.pricingStrategy = pricingStrategy;
    }

    public void setVendingState(VendingState vendingState) {
        this.vendingState = vendingState;
    }

    void addCash(Money money) {
        if (paymentStrategy instanceof CashPaymentStrategy cash) {
            cash.insertedCash(money);
        } else {
            throw new IllegalStateException("Current payment method is not cash");
        }
    }

    void refundMoney() {
        System.out.println(paymentStrategy.refund());
    }

    void physicalDispensing() {
        InventorySlot inventorySlot = inventoryRepository.getSlot(selectedProductCode);
        inventorySlot.decrement();
        System.out.println("Dispensing : " + inventorySlot.getProduct().getName());
    }

    void clearSelection() {
        selectedProductCode = null;
    }

    void processSelection(String selectedProductCode) {
        InventorySlot inventorySlot = inventoryRepository.getSlot(selectedProductCode);

        if (inventorySlot == null || inventorySlot.isInventorySlotEmpty()) {
            System.out.println("Product not available.");
            refundMoney();
            setVendingState(new IdleState());
            return;
        }

        Money price = pricingStrategy.getPrice(inventorySlot.getProduct());
        boolean paid = paymentStrategy.pay(price);

        if (!paid) {
            System.out.println("Payment failed for price : " + inventorySlot.getProduct().getName());
            return;
        }

        this.selectedProductCode = selectedProductCode;
        setVendingState(new DispensingState());
        vendingState.dispense(this);
    }

    void doDispenseFlow() {

    }

    public void insertCash(Money money) {
        lock.lock();
        try {
            vendingState.insertCash(this, money);
        } finally {
            lock.unlock();
        }
    }

    public void selectProductCode(String selectedProductCode) {
        lock.lock();
        try {
            vendingState.selectProduct(this, selectedProductCode);
        } finally {
            lock.unlock();
        }
    }

    public void cancel() {
        lock.lock();
        try {
            vendingState.cancel(this);
        } finally {
            lock.unlock();
        }
    }

    public void markOutOfOrder() {
        lock.lock();
        try {
            this.vendingState = new OutOfOrderState();
        } finally {
            lock.unlock();
        }
    }
}
