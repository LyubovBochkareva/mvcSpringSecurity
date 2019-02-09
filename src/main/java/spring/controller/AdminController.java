package spring.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import spring.model.Role;
import spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import spring.service.abstr.RoleService;
import spring.service.abstr.UserService;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


@RequestMapping(value = "/admin")
@Controller
public class AdminController {

    @Autowired
    private final UserService userServiceImpl;
    @Autowired
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping()
    public ModelAndView getAdminIndexPage() {
        return new ModelAndView("admin");
    }

    @GetMapping(value = "/users")
    public ModelAndView getAllUsers() {
        ModelAndView modelAndView = new ModelAndView("allUsers");
        modelAndView.addObject("listUsers", userServiceImpl.getAllUser());
        return modelAndView;
    }


    @GetMapping(value = "/users/{id}/update")
    public ModelAndView updateUserGet(@PathVariable("id") Long id) {
        User user = userServiceImpl.getUserById(id);
        ModelAndView modelAndView = new ModelAndView("updateUser");
        modelAndView.addObject("user", user);
        Role roleUser = roleService.getRoleByRoleName("USER");
        Role roleAdmin = roleService.getRoleByRoleName("ADMIN");
        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);
        roles.add(roleAdmin);
        modelAndView.addObject("allRoles", roles);
        return modelAndView;
    }

        @RequestMapping(value = "/users/{id}/update", method = RequestMethod.POST)
        public String  updateUserPost(@PathVariable("id") Long id, User userFromPage) {
            userFromPage.setId(id);
            Set<Role> rolesUser = userFromPage.getRoles();
            if(rolesUser.contains("ADMIN")){
                Role roleAdmin = roleService.getRoleByRoleName("ADMIN");
                rolesUser.remove("ADMIN");
                rolesUser.add(roleAdmin);
            }
            if(rolesUser.contains("USER")){
                Role roleUser  = roleService.getRoleByRoleName("USER");
                rolesUser.remove("USER");
                rolesUser.add(roleUser);
            }
            userFromPage.setRoles(rolesUser);
            userServiceImpl.updateUser(userFromPage);
            return "redirect:/admin/users";
        }


    @GetMapping(value = "/users/{id}/delete")
    public String deleteUserGet(@PathVariable("id") Long id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/users/add")
    public ModelAndView addUserGet() {
        ModelAndView modelAndView = new ModelAndView("addUser");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping(value = "/users/add")
    public String addUserPost(User user) {
        userServiceImpl.addUser(user);
        return "redirect:/admin/users";
    }


    @RequestMapping(value = "/user", method = RequestMethod.GET)
        public ModelAndView user(ModelAndView modelAndView){
        modelAndView.setViewName("user");
        return modelAndView;
    }

}
