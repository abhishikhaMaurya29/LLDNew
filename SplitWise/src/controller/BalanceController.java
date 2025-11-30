package controller;

import model.GroupBalance;
import model.UserToUserBalance;
import service.BalanceQueryService;

import java.util.List;

public class BalanceController {
    private final BalanceQueryService queryService;
    public BalanceController(BalanceQueryService queryService) {
        this.queryService = queryService;
    }

    public List<GroupBalance> getGroupBalances(Long groupId) {
        return queryService.getGroupBalances(groupId);
    }

    public List<GroupBalance> getUserBalances(Long userId) {
        return queryService.getUserBalances(userId);
    }

    public List<UserToUserBalance> getUserToUserBalances(Long userId) {
        return queryService.getUserToUserBalances(userId);
    }
}