package controller;

import dto.CreateUserRequest;
import model.User;
import service.UserService;

public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User createUser(CreateUserRequest req) {
        User u = new User(null, req.getName(), req.getEmail());
        Long id = userService.createUser(u);
        return new User(id, u.getName(), u.getEmail());
    }

//    public User getUser(Long id) {
//        return userService.findById(id);
//    }
}
