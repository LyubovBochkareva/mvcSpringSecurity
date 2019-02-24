package spring.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import spring.converter.UserConverterService;
import spring.dao.abstr.UserDao;
import spring.dto.UserDTO;
import spring.dto.UserResponse;
import spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.service.abstr.UserService;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    private UserConverterService userConverterService;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }



    @Override
    public UserDTO getUserById(Long id) {
        return userConverterService.getUserByEntity(userDao.getUserById(id));
    }


    @Override
    @Transactional
    public List<UserDTO> getAllUser() {
        return userConverterService.getUserByEntity(userDao.getAllUser());
    }


    @Override
    public void updateUser(UserDTO user) {

        userDao.updateUser(userConverterService.getUserByUserDTO(user));
    }


    @Transactional
    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    @Transactional
    public void addUser(UserDTO userDTO) {
        userDao.addUser(userConverterService.getUserByUserDTO(userDTO));
    }


    @Override
    public User getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

    @Override
    public UserResponse addListUsers(List<UserDTO> userDTOList) {
        UserResponse userResponse = new UserResponse();
        for (UserDTO userDTO : userDTOList) {
            if (userDao.getUserByLogin(userDTO.getUsername()) != null) {
                userResponse.setCodeStatus(HttpStatus.NO_CONTENT);
                userResponse.setMessageStatus("login " + userDTO.getUsername() + " busy");
            } else {
                userDTO.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
                userDao.addUser(userConverterService.getUserByUserDTO(userDTO));
            }
            if (userResponse.getCodeStatus() != (HttpStatus.NO_CONTENT)) {
                userResponse.setCodeStatus(HttpStatus.OK);
                userResponse.setMessageStatus("OK");
            }
        }
        return userResponse;
    }

    @Override
    public UserResponse deleteListUsers(List<UserDTO> userDTOList) {
        UserResponse userResponse = new UserResponse();
        for (UserDTO userDTO : userDTOList) {
            if (userDao.getUserById(userDTO.getId()) != null) {
                userDao.deleteUser(userDTO.getId());
            } else {
                userResponse.setCodeStatus(HttpStatus.NO_CONTENT);
                userResponse.setMessageStatus("user " + userDTO.getUsername() + " not found");
            }
            if (userResponse.getCodeStatus() != (HttpStatus.NO_CONTENT)) {
                userResponse.setCodeStatus(HttpStatus.OK);
                userResponse.setMessageStatus("OK");
            }
        }
        return userResponse;
    }
}

