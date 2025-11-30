package impl.repo;

import model.User;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryUserRepository implements UserRepository {
    Map<Long, User> users = new ConcurrentHashMap<>();

    private final AtomicLong idGen = new AtomicLong(0);

    @Override
    public Long save(User user) {
        Long id = idGen.getAndIncrement();
        user.setId(id);
        users.put(id, user);
        return id;
    }

    @Override
    public User findById(Long userId) {
        return users.get(userId);
    }

    @Override
    public List<User> findByAllIds(List<Long> userIds) {
        List<User> res = new ArrayList<>();
        for (User user : users.values()) {
            if (users.containsKey(user.getId()))
                res.add(user);
        }

        return res;
    }
}
