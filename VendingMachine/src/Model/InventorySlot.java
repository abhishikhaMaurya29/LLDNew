package Model;

public class InventorySlot {
    private final Product product;
    private int qty;

    public InventorySlot(Product product, int qty) {
        this.product = product;
        this.qty = Math.max(0, qty);
    }

    public Product getProduct() {
        return product;
    }

    public int getQty() {
        return qty;
    }

    public boolean isInventorySlotEmpty() {
        return qty == 0;
    }

    public void decrement() {
        if (qty == 0) {
            throw new IllegalStateException("Out of stock");
        }

        qty--;
    }

    public void refill(int qty) {
        this.qty += qty;
    }
}