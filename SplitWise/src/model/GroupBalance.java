package model;

import java.math.BigDecimal;

public class GroupBalance {
    private final Long userId;
    private final Long groupId;
    private BigDecimal netBalance = BigDecimal.ZERO;

    public GroupBalance(Long userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public void addBalance(BigDecimal delta) {
        netBalance = netBalance.add(delta);
    }

    public void subtractBalance(BigDecimal delta) {
        netBalance = netBalance.subtract(delta);
    }

    public Long getUserId() {
        return userId;
    }

    public Long getGroupId() {
        return groupId;
    }
}