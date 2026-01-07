package model.Order;

public class AbstractOrderState implements OrderState {
    @Override
    public OrderStatus status() {
        return null;
    }

    @Override
    public void pay(OrderContext ctx) {
        invalid();
    }

    @Override
    public void accept(OrderContext ctx) {
        invalid();
    }

    @Override
    public void prepare(OrderContext ctx) {
        invalid();
    }

    @Override
    public void dispatch(OrderContext ctx) {
        invalid();
    }

    @Override
    public void deliver(OrderContext ctx) {
        invalid();
    }

    @Override
    public void cancel(OrderContext ctx) {
        invalid();
    }

    protected void invalid() {
        throw new IllegalStateException("Invalid state transition.");
    }
}