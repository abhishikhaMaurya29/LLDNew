package impl.repo;

import model.GroupBalance;
import repository.GroupBalanceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryGroupBalanceRepository implements GroupBalanceRepository {
    private final Map<String, GroupBalance> store = new ConcurrentHashMap<>();

    private String key(Long g, Long u) {
        return g + "_" + u;
    }

    @Override
    public GroupBalance find(Long groupId, Long userId) {
        return store.computeIfAbsent(key(groupId, userId), _ -> new GroupBalance(groupId, userId));
    }

    @Override
    public void save(GroupBalance groupBalance) {
        store.put(key(groupBalance.getGroupId(), groupBalance.getUserId()), groupBalance);
    }

    @Override
    public List<GroupBalance> findByUserId(Long userId) {
        List<GroupBalance> groupBalances = new ArrayList<>();

        for (GroupBalance groupBalance : store.values()) {
            if (Objects.equals(groupBalance.getUserId(), userId)) {
                groupBalances.add(groupBalance);
            }
        }

        return groupBalances;
    }

    @Override
    public List<GroupBalance> findByGroupId(Long groupId) {
        List<GroupBalance> groupBalances = new ArrayList<>();

        for (GroupBalance groupBalance : store.values()) {
            if (Objects.equals(groupBalance.getGroupId(), groupId)) {
                groupBalances.add(groupBalance);
            }
        }

        return groupBalances;
    }
}
