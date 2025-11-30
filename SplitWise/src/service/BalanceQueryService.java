package service;

import model.GroupBalance;
import model.UserToUserBalance;
import repository.GroupBalanceRepository;
import repository.UserToUserBalanceRepository;

import java.util.List;

public class BalanceQueryService {
    private final UserToUserBalanceRepository userToUserBalanceRepository;
    private final GroupBalanceRepository groupBalanceRepository;

    public BalanceQueryService(UserToUserBalanceRepository userToUserBalanceRepository,
                               GroupBalanceRepository groupBalanceRepository) {
        this.userToUserBalanceRepository = userToUserBalanceRepository;
        this.groupBalanceRepository = groupBalanceRepository;
    }

    public List<GroupBalance> getGroupBalances(Long groupId) {
        return groupBalanceRepository.findByGroupId(groupId);
    }

    public List<GroupBalance> getUserBalances(Long userId) {
        return groupBalanceRepository.findByUserId(userId);
    }

    public List<UserToUserBalance> getUserToUserBalances(Long userId) {
        return userToUserBalanceRepository.findByUserId(userId);
    }
}
