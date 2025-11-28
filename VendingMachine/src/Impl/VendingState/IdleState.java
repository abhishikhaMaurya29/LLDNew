package Impl.VendingState;

import Model.Money;

public class IdleState implements VendingState {
    @Override
    public void insertCash(VendingMachine vendingMachine, Money amount) {
        vendingMachine.addCash(amount);
        vendingMachine.setVendingState(new HasMoneyState());
    }

    @Override
    public void selectProduct(VendingMachine vendingMachine, String product_code) {
        throw new IllegalStateException("Insert money first");
    }

    @Override
    public void cancel(VendingMachine vendingMachine) {
        throw new IllegalStateException("Nothing to cancel");
    }

    @Override
    public void dispense(VendingMachine vendingMachine) {
        throw new IllegalStateException("Nothing to dispense");
    }
}
