package spring.converter;

import spring.dto.UserDTO;
import spring.model.User;

import java.util.List;

public interface UserConverterService {

    UserDTO getUserByEntity(User user);

    List<UserDTO> getUserByEntity(List<User> user);

    User getUserByUserDTO(UserDTO userDTO);

    List<User> getUserByUserDTO(List<UserDTO> userDTO);


}
