package Impl.VendingState;

import Model.Money;

public class OutOfOrderState implements VendingState {
    @Override
    public void insertCash(VendingMachine vendingMachine, Money amount) {
        throw new IllegalStateException("Machine is out of order.");
    }

    @Override
    public void selectProduct(VendingMachine vendingMachine, String product_code) {
        throw new IllegalStateException("Machine is out of order.");
    }

    @Override
    public void cancel(VendingMachine vendingMachine) {
        vendingMachine.refundMoney();
    }

    @Override
    public void dispense(VendingMachine vendingMachine) {
        throw new IllegalStateException("Machine is out of order.");
    }
}
