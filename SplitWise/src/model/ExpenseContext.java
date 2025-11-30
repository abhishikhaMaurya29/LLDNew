package model;

import java.math.BigDecimal;
import java.util.List;

public class ExpenseContext {
    private final User paidBy;
    private final BigDecimal amount;
    private final List<User> participants;
    private final List<BigDecimal> exactAmounts;
    private final List<BigDecimal> percents;

    public ExpenseContext(User paidBy, BigDecimal amount, List<User> participants, List<BigDecimal> exactAmounts,
                          List<BigDecimal> percents) {
        this.paidBy = paidBy;
        this.amount = amount;
        this.participants = participants;
        this.exactAmounts = exactAmounts;
        this.percents = percents;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public List<BigDecimal> getExactAmounts() {
        return exactAmounts;
    }

    public List<BigDecimal> getPercents() {
        return percents;
    }
}
