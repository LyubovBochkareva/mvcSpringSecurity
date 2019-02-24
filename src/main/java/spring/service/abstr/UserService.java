package spring.service.abstr;


import spring.dto.UserDTO;
import spring.dto.UserResponse;
import spring.model.User;

import java.util.List;

public interface UserService{
    void addUser(UserDTO user);
    List<UserDTO> getAllUser();
    void deleteUser(Long id);
    UserDTO getUserById(Long id);
    void updateUser(UserDTO user);
    User getUserByLogin(String login);
    UserResponse addListUsers(List<UserDTO> userDTOList);
    UserResponse deleteListUsers(List<UserDTO> userDTOList);
}
