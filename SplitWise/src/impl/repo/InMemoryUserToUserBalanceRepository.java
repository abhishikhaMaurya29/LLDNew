package impl.repo;

import model.UserToUserBalance;
import repository.UserToUserBalanceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserToUserBalanceRepository implements UserToUserBalanceRepository {
    Map<String, UserToUserBalance> map = new ConcurrentHashMap<>();

    private String getKey(Long uId1, Long uId2) {
        Long min = Math.min(uId1, uId2);
        Long max = Math.max(uId1, uId2);

        return min + "_" + max;
    }

    @Override
    public void save(UserToUserBalance userToUserBalance) {
        map.put(getKey(userToUserBalance.getUser1(), userToUserBalance.getUser2()), userToUserBalance);
    }

    @Override
    public List<UserToUserBalance> findByUserId(Long userId) {
        List<UserToUserBalance> list = new ArrayList<>();

        for (UserToUserBalance b : map.values()) {
            if (b.getUser1().equals(userId) || b.getUser2().equals(userId)) {
                list.add(b);
            }
        }

        return list;
    }

    @Override
    public UserToUserBalance find(Long userId1, Long userId2) {
        return map.get(getKey(userId1, userId2));
    }
}
