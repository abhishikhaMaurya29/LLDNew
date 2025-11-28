package Impl.Inventory;

import Model.InventorySlot;

import java.util.HashMap;
import java.util.Map;

public class InMemoryInventoryRepository implements InventoryRepository {
    private final Map<String, InventorySlot> slotMap = new HashMap<>();

    public void addSlot(InventorySlot slot) {
        slotMap.put(slot.getProduct().getCode(), slot);
    }

    public void removeSlot(String productCode) {
        if (slotMap.remove(productCode) == null) {
            throw new IllegalStateException("Product code is not valid");
        }
    }

    public InventorySlot getSlot(String productCode) {
        return slotMap.get(productCode);
    }
}