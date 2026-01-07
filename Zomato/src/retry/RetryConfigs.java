package retry;

public class RetryConfigs {
    public static final RetryPolicy PAYMENT_RETRY =
            new RetryPolicy(3, 200, 2.0);

    public static final RetryPolicy DELIVERY_RETRY =
            new RetryPolicy(5, 500, 1.5);
}