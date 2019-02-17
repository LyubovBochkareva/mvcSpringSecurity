package spring.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.converter.RoleConverterService;
import spring.converter.UserConverterService;
import spring.dto.UserDTO;
import spring.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserConverterServiceImpl implements UserConverterService {

    @Autowired
    private RoleConverterService roleConverterService;

    @Override
    public UserDTO getUserByEntity(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setName(user.getName());
        userDTO.setAge(user.getAge());
        userDTO.setRoles(roleConverterService.getRoleByEntity(user.getRoles()));
        return userDTO;
    }

    @Override
    public List<UserDTO> getUserByEntity(List<User> userList) {
        List<UserDTO> userDTOList = new ArrayList<>();
        for(User user: userList){
            UserDTO userDTO = getUserByEntity(user);
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    @Override
    public User getUserByUserDTO(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setAge(userDTO.getAge());
        user.setRoles(roleConverterService.getRoleByRoleDTO(userDTO.getRoles()));
        return user;
    }

    @Override
    public List<User> getUserByUserDTO(List<UserDTO> userDTOList) {
        List<User> userList = new ArrayList<>();
        for(UserDTO userDTO: userDTOList){
            User user = getUserByUserDTO(userDTO);
            userList.add(user);
        }
        return userList;
    }

}
