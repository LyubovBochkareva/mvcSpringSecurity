package spring.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import spring.converter.RoleConverterService;
import spring.converter.UserConverterService;
import spring.dao.abstr.UserDao;
import spring.dto.RoleDTO;
import spring.dto.UserDTO;
import spring.dto.UserResponse;
import spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.service.abstr.RoleService;
import spring.service.abstr.UserService;
import spring.util.validation.PasswordValidator;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    private UserConverterService userConverterService;
    @Autowired
    private RoleService roleService;

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
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
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
    public UserDTO getUserDTOByLogin(String login) {
        return userConverterService.getUserByEntity(userDao.getUserByLogin(login));
    }

    @Override
    public UserResponse addListUsersRest(List<UserDTO> userDTOList) {
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
    public UserResponse deleteListUsersRest(List<UserDTO> userDTOList) {
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

    @Override
    public ResponseEntity addUserRestPost(UserDTO userDTO) {
        if (userDTO.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userDTO.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        userDao.addUser(userConverterService.getUserByUserDTO(userDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity deleteUserRestGet(Long id) {
        UserDTO userDTO = null;
        if (id > 0) {
            userDTO = userConverterService.getUserByEntity(userDao.getUserById(id));
        }
        if (userDTO != null) {
            userDao.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ModelAndView getRegistrationForm(UserDTO userFromPage, String confirmPassword, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView("registration");
        model.addObject("user", userFromPage);

        boolean isConfEqualToPass =
                PasswordValidator.validatePassword(userFromPage.getPassword(), confirmPassword);

        if (bindingResult.hasErrors()) {
            return model;
        } else if (!isConfEqualToPass) {
            model.addObject("errorConfirm", "Error confirm pass");
            return model;
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userFromPage.setPassword(passwordEncoder.encode(userFromPage.getPassword()));


        String username = userFromPage.getUsername();
        boolean isUser = userDao.getUserByLogin(username) != null;

        if (isUser) {
            model.addObject("errorUsername", "Duplicate email");
            return model;
        } else {
            RoleDTO role = roleService.getRoleByRoleName("USER");
            List<RoleDTO> roles = new ArrayList<>();
            roles.add(role);
            userFromPage.setRoles(roles);
            userDao.addUser(userConverterService.getUserByUserDTO(userFromPage));
            model.addObject("congratulations", "registration completed successfully");
        }
        return model;
    }
}

