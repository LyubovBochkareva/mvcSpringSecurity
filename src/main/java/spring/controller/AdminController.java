package spring.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import spring.service.abstr.RoleService;
import spring.service.abstr.UserService;



@RequestMapping(value = "/admin")
@Controller
public class AdminController {

    @Autowired
    private final UserService userServiceImpl;
    @Autowired
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    @GetMapping()
    public ModelAndView getAllUsers() {
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("listUsers", userServiceImpl.getAllUser());
        return modelAndView;
    }


    @GetMapping(value = "/users/update/{id}")
    public ModelAndView updateUserGet(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("updateUser");
        modelAndView.addObject("userDTO", userServiceImpl.getUserById(id));
        modelAndView.addObject("allRoles", roleService.getAllRoles());
        return modelAndView;
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public String updateUserPost(UserDTO userDTO) {
        userServiceImpl.updateUser(userDTO);
        return "redirect:/admin/users";
    }


    @GetMapping(value = "/users/delete/{id}")
    public String deleteUserGet(@PathVariable("id") Long id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "#add")
    public ModelAndView addUserGet() {
        ModelAndView modelAndView = new ModelAndView("addUser");
        modelAndView.addObject("user", new UserDTO());
        modelAndView.addObject("allRoles", roleService.getAllRoles());
        return modelAndView;
    }

    @PostMapping(value = "/users/add")
    public String addUserPost(UserDTO user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userServiceImpl.addUser(user);
        return "redirect:/admin/users";
    }


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView user(ModelAndView modelAndView) {
        modelAndView.setViewName("user");
        return modelAndView;
    }

}
