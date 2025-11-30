package dto;

import model.Group;
import model.User;

import java.math.BigDecimal;

public class SettlementRequest {
    private final User fromUser;
    private final User toUser;
    private final Group group;
    private final String referenceId;
    private final BigDecimal amount;

    public SettlementRequest(User fromUser, User toUser, Group group, String referenceId, BigDecimal amount) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.group = group;
        this.referenceId = referenceId;
        this.amount = amount;
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
}
