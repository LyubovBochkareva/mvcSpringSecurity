package spring.dao;

import spring.model.User;

import java.util.List;

public interface UserDao {
    User getUserById(Long id);
    User getUserByLogin(String login);
    List<User> getAllUser();
    void addUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);
    public void saveOrUpdate(User user);
}
