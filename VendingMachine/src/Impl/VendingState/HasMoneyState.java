package Impl.VendingState;

import Model.Money;

public class HasMoneyState implements VendingState {
    @Override
    public void insertCash(VendingMachine vendingMachine, Money amount) {
        vendingMachine.addCash(amount);
    }

    @Override
    public void selectProduct(VendingMachine vendingMachine, String product_code) {
        vendingMachine.processSelection(product_code);
    }

    @Override
    public void cancel(VendingMachine vendingMachine) {
        vendingMachine.refundMoney();
        vendingMachine.setVendingState(new IdleState());
    }

    @Override
    public void dispense(VendingMachine vendingMachine) {
        vendingMachine.doDispenseFlow();
    }
}
