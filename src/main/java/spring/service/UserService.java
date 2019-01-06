package spring.service;

import spring.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    List<User> getAllUser();
    void deleteUser(Long id);
    User getUserById(Long id);
    User updateUser(User user);
    public void saveOrUpdate(User user);
}
