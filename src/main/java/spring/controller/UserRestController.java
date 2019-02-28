package spring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.dto.UserDTO;
import spring.dto.UserResponse;
import spring.service.abstr.UserService;

import java.util.List;

@RequestMapping(value = "/api/users")
@RestController
public class UserRestController {

    private final UserService userServiceImpl;

    @Autowired
    public UserRestController(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    @GetMapping(value = "/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOList = userServiceImpl.getAllUser();
        return new ResponseEntity<>(userDTOList, userDTOList != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity updateUserPost(@RequestBody UserDTO userDTO) {
        if (userDTO.getId() != null) {
            userServiceImpl.updateUser(userDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity deleteUserGet(@PathVariable("id") Long id) {
       return userServiceImpl.deleteUserRestGet(id);
    }


    @PostMapping(value = "/add")
    public ResponseEntity addUserPost(@RequestBody UserDTO userDTO) {
        return userServiceImpl.addUserRestPost(userDTO);
    }


    @DeleteMapping(value = "/deleteList")
    public ResponseEntity<UserResponse> deleteUserList(@RequestBody List<UserDTO> userDTOList) {
        UserResponse response = userServiceImpl.deleteListUsersRest(userDTOList);
        return new ResponseEntity<>(response, response.getCodeStatus());
    }


    @PostMapping(value = "/addList")
    public ResponseEntity<UserResponse> addUserList(@RequestBody List<UserDTO> userDTOList) {
        UserResponse response = userServiceImpl.addListUsersRest(userDTOList);
        return new ResponseEntity<>(response, response.getCodeStatus());
    }
}
