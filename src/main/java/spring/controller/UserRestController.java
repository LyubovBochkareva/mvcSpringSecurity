package spring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.dto.UserDTO;
import spring.dto.UserResponse;
import spring.service.abstr.UserService;

import java.util.List;

@RequestMapping(value = "/api/admin")
@RestController
public class UserRestController {

    @Autowired
    private UserService userServiceImpl;


    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOList = userServiceImpl.getAllUser();
        return new ResponseEntity<>(userDTOList, userDTOList != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public ResponseEntity updateUserPost(@RequestBody UserDTO userDTO) {
        if (userDTO.getId() != null) {
            userServiceImpl.updateUser(userDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping(value = "/users/delete/{id}")
    public ResponseEntity deleteUserGet(@PathVariable("id") Long id) {
        UserDTO userDTO = null;
        if (id > 0) {
            userDTO = userServiceImpl.getUserById(id);
        }
        if (userDTO != null) {
            userServiceImpl.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping(value = "/users/add")
    public ResponseEntity addUserPost(@RequestBody UserDTO userDTO) {
        if (userDTO.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userDTO.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        userServiceImpl.addUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping(value = "/users/deleteList")
    public ResponseEntity<UserResponse> deleteUserList(@RequestBody List<UserDTO> userDTOList) {
        UserResponse response = userServiceImpl.deleteListUsers(userDTOList);
        return new ResponseEntity<>(response, response.getCodeStatus());
    }


    @PostMapping(value = "/users/addList")
    public ResponseEntity<UserResponse> addUserList(@RequestBody List<UserDTO> userDTOList) {
        UserResponse response = userServiceImpl.addListUsers(userDTOList);
        return new ResponseEntity<>(response, response.getCodeStatus());
    }
}
