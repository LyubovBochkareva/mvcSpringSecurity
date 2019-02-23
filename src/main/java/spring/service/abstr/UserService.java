package spring.service.abstr;

import spring.dto.UserDTO;
import spring.model.Role;
import spring.model.User;

import java.util.List;
import java.util.Set;

public interface UserService{
    void addUser(UserDTO user);
    List<UserDTO> getAllUser();
    void deleteUser(Long id);
    UserDTO getUserById(Long id);
    void updateUser(UserDTO user);
    public void saveOrUpdate(User user);
    public User getUserByLogin(String login);
}
