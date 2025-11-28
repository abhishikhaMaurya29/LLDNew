package Impl.VendingState;

import Model.Money;

public interface VendingState {
    void insertCash(VendingMachine vendingMachine, Money amount);

    void selectProduct(VendingMachine vendingMachine, String product_code);

    void cancel(VendingMachine vendingMachine);

    void dispense(VendingMachine vendingMachine);
}
