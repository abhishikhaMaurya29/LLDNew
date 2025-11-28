package Impl.VendingState;

import Model.Money;

public class DispensingState implements VendingState {
    @Override
    public void insertCash(VendingMachine vendingMachine, Money amount) {
        throw new IllegalStateException("Dispensing in progress.");
    }

    @Override
    public void selectProduct(VendingMachine vendingMachine, String product_code) {
        throw new IllegalStateException("Dispensing in progress.");
    }

    @Override
    public void cancel(VendingMachine vendingMachine) {
        throw new IllegalStateException("Dispensing in progress.");
    }

    @Override
    public void dispense(VendingMachine vendingMachine) {
        vendingMachine.physicalDispensing();
        vendingMachine.refundMoney();
        vendingMachine.clearSelection();
        vendingMachine.setVendingState(new IdleState());
    }
}
