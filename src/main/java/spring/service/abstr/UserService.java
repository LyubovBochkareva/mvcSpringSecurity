package spring.service.abstr;

import spring.model.Role;
import spring.model.User;

import java.util.List;
import java.util.Set;

public interface UserService{
    void addUser(User user);
    List<User> getAllUser();
    void deleteUser(Long id);
    User getUserById(Long id);
    User updateUser(User user);
    public void saveOrUpdate(User user);
    public User getUserByLogin(String login);
}
