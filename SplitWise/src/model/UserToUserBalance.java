package model;

import java.math.BigDecimal;

public class UserToUserBalance {
    private Long user1;
    private Long user2;
    private BigDecimal amount;

    public UserToUserBalance() {
    }

    public UserToUserBalance(Long user1, Long user2, BigDecimal delta) {
        this.user1 = user1;
        this.user2 = user2;
        this.amount = delta;
    }

    public Long getUser1() {
        return user1;
    }

    public Long getUser2() {
        return user2;
    }

    public void add(BigDecimal delta) {
        amount = amount.add(delta);
    }

    public void subtract(BigDecimal delta) {
        amount = amount.subtract(delta);
    }
}
