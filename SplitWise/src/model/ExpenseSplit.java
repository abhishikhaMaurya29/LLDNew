package model;

import java.math.BigDecimal;

public class ExpenseSplit {
    private Long id;
    private User user;
    private BigDecimal amount;

    public ExpenseSplit() {
    }

    public ExpenseSplit(User user, BigDecimal amount) {
        this.user = user;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
