package spring.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import spring.converter.UserConverterService;
import spring.dao.abstr.UserDao;
import spring.dto.UserDTO;
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
    public void saveOrUpdate(User user) {
        userDao.saveOrUpdate(user);
    }

    @Override
    public User getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }
}

