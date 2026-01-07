package model;

import payment.PaymentType;

public final class PaymentRequest {
    private final String orderId;
    private final String idempotencyKey;
    private final PaymentType paymentType;

    public PaymentRequest(String orderId, String idempotencyKey, PaymentType paymentType) {
        this.orderId = orderId;
        this.idempotencyKey = idempotencyKey;
        this.paymentType = paymentType;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }
}