package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Expense {
    private Long id;
    private String description;
    private BigDecimal amount;
    private User paidBy;
    private Group group;
    private List<ExpenseSplit> splits;
    private SplitType splitType;
    private LocalDateTime createdBy;

    public Expense(Long id, String description, BigDecimal amount, User paidBy, Group group, List<ExpenseSplit> splits, SplitType splitType, LocalDateTime createdBy) {
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