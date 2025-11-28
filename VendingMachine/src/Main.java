import Impl.Inventory.InventoryRepository;
import Impl.PaymentStrategy.PaymentType;
import Impl.PricingStrategy.DefaultPricingStrategy;
import Impl.PricingStrategy.DiscountPricingStrategy;
import Impl.PricingStrategy.MembershipPricingStrategy;
import Impl.PricingStrategy.PricingStrategy;
import Impl.VendingState.VendingMachine;
import Impl.Inventory.InMemoryInventoryRepository;
import Model.InventorySlot;
import Model.Money;
import Model.Product;

import java.util.Currency;

public class Main {
    public static void main(String[] args) {
        Currency inr = Currency.getInstance("INR");

        Product coke = new Product("Coke", "A1", new Money(15000, inr));  // ₹150
        Product chips = new Product("Chips", "B1", new Money(10000, inr)); // ₹100

        InventoryRepository inventoryRepository = new InMemoryInventoryRepository();
        inventoryRepository.addSlot(new InventorySlot(coke, 10));
        inventoryRepository.addSlot(new InventorySlot(chips, 5));

        // Pricing: 10% discount + ₹5 membership off
        PricingStrategy pricing = new MembershipPricingStrategy(
                new DiscountPricingStrategy(
                        new DefaultPricingStrategy(),
                        0.10
                ),
                new Money(500, inr) // ₹5 off
        );

        VendingMachine machine = new VendingMachine(
                inventoryRepository,
                pricing,
                PaymentType.CASH,
                inr
        );

        machine.insertCash(new Money(20000, inr));   // ₹200 inserted
        machine.selectProductCode("A1");                 // Buy Coke

        // E.g. user cancels afterward
        machine.cancel();
    }
}