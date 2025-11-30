package model;

import java.math.BigDecimal;

public class Settlement {
    private Long id;
    private User fromUser;
    private User toUser;
    private Group group;
    private String referenceId;
    private BigDecimal amount;

    public Settlement(Long id, User fromUser, User toUser, Group group, String referenceId, BigDecimal amount) {
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.group = group;
        this.referenceId = referenceId;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public User getFromUser() {
        return fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public Group getGroup() {
        return group;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
