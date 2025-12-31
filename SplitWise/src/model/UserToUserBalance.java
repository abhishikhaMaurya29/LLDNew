package model;

import java.math.BigDecimal;

public class UserToUserBalance {
    private Long userId1;
    private Long userId2;
    private BigDecimal amount;

    public UserToUserBalance() {
    }

    public UserToUserBalance(Long userId1, Long userId2, BigDecimal delta) {
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.amount = delta;
    }

    public Long getUserId1() {
        return userId1;
    }

    public Long getUserId2() {
        return userId2;
    }

    public void add(BigDecimal delta) {
        amount = amount.add(delta);
    }

    public void subtract(BigDecimal delta) {
        amount = amount.subtract(delta);
    }
}
