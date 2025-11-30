package repository;

import model.User;

import java.util.List;

public interface UserRepository {
    Long save(User user);

    User findById(Long userId);

    List<User> findByAllIds(List<Long> userIds);
}
