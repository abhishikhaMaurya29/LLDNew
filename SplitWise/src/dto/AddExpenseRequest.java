package dto;

import model.Group;
import model.SplitType;
import model.User;

import java.math.BigDecimal;
import java.util.List;

public class AddExpenseRequest {
    private Group group;
    private User paidBy;
    private String description;
    private SplitType splitType;
    private BigDecimal amount;
    private List<User> participants; // Because not all group members are participants in every expense.
    private List<BigDecimal> exactAmounts;
    private List<BigDecimal> percents;

    public AddExpenseRequest(Group group, User paidBy, String description, SplitType splitType, BigDecimal amount, List<User> participants, List<BigDecimal> exactAmounts, List<BigDecimal> percents) {
        this.group = group;
        this.paidBy = paidBy;
        this.description = description;
        this.splitType = splitType;
        this.amount = amount;
        this.participants = participants;
        this.exactAmounts = exactAmounts;
        this.percents = percents;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(User paidBy) {
        this.paidBy = paidBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SplitType getSplitType() {
        return splitType;
    }

    public void setSplitType(SplitType splitType) {
        this.splitType = splitType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public List<BigDecimal> getExactAmounts() {
        return exactAmounts;
    }

    public void setExactAmounts(List<BigDecimal> exactAmounts) {
        this.exactAmounts = exactAmounts;
    }

    public List<BigDecimal> getPercents() {
        return percents;
    }

    public void setPercents(List<BigDecimal> percents) {
        this.percents = percents;
    }
}

//ExactAmounts and percents tell how much each person pays,
//but how do we know WHO pays WHICH amount?

//the same index in all lists corresponds to the same person

//First — NO, the user cannot send both percents and exactAmounts at the same time.
//This is NOT allowed.
//Only ONE must be provided depending on the splitType