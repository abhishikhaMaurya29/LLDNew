package repository;

import model.UserToUserBalance;

import java.util.List;

public interface UserToUserBalanceRepository {
    void save(UserToUserBalance userToUserBalance);

    List<UserToUserBalance> findByUserId(Long userId);

    UserToUserBalance find(Long groupId, Long userId);
}