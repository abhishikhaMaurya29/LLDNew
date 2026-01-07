package model.Order;

public interface OrderState {
    OrderStatus status();

    void pay(OrderContext ctx);

    void accept(OrderContext ctx);

    void prepare(OrderContext ctx);

    void dispatch(OrderContext ctx);

    void deliver(OrderContext ctx);

    void cancel(OrderContext ctx);
}