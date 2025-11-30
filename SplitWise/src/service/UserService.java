package service;

import impl.repo.InMemoryUserRepository;
import model.User;

public class UserService {
    private InMemoryUserRepository userRepository;

    public Long createUser(User user) {
        return userRepository.save(user);
    }
}
