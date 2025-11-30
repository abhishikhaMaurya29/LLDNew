package repository;

import model.GroupBalance;

import java.util.List;

public interface GroupBalanceRepository {
    GroupBalance find(Long groupId, Long userId);

    void save(GroupBalance groupBalance);

    List<GroupBalance> findByUserId(Long userId);

    List<GroupBalance> findByGroupId(Long groupId);
}
