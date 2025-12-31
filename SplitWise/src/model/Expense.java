package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Expense {
    private Long id;
    private final String description;
    private final BigDecimal amount;
    private final User paidBy;
    private final Group group;
    private final List<ExpenseSplit> splits;
    private final SplitType splitType;
    private final LocalDateTime createdBy;

    public Expense(Long id, String description, BigDecimal amount, User paidBy, Group group, List<ExpenseSplit> splits,
                   SplitType splitType, LocalDateTime createdBy) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.paidBy = paidBy;
        this.group = group;
        this.splits = splits;
        this.splitType = splitType;
        this.createdBy = createdBy;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ExpenseSplit> getSplits() {
        return splits;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public Group getGroup() {
        return group;
    }
}