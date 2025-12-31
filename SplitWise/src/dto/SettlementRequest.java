package dto;

import model.Group;
import model.User;

import java.math.BigDecimal;

public record SettlementRequest(User fromUser, User toUser, Group group, String referenceId, BigDecimal amount) {
}
