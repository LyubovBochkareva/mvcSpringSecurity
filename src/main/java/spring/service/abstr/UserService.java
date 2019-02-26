package spring.service.abstr;


import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
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
    UserResponse addListUsersRest(List<UserDTO> userDTOList);
    UserResponse deleteListUsersRest(List<UserDTO> userDTOList);
    ResponseEntity addUserRestPost(UserDTO userDTO);
    ResponseEntity deleteUserRestGet(Long id);
    ModelAndView getRegistrationForm(UserDTO userDTO, String confirmPassword, BindingResult bindingResult);
}
