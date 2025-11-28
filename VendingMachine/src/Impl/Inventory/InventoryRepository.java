package Impl.Inventory;

import Model.InventorySlot;

public interface InventoryRepository {
    void addSlot(InventorySlot slot);

    void removeSlot(String product_code);

    InventorySlot getSlot(String productCode);
}
