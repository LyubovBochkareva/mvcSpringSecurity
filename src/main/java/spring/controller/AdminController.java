package spring.controller;

import com.google.gson.Gson;
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
import java.util.List;
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


    @GetMapping(value = "/users/update/{id}")
    public ModelAndView updateUserGet(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("updateUser");
        Gson gson = new Gson();
        System.out.println(gson.toJson(userServiceImpl.getUserById(id)));
        modelAndView.addObject("user", userServiceImpl.getUserById(id));
        modelAndView.addObject("allRoles", roleService.getAllRoles());
        return modelAndView;
    }

        @RequestMapping(value = "/users/update", method = {RequestMethod.POST, RequestMethod.OPTIONS}, headers = "Accept=application/json", produces = {"application/json; charset=UTF-8"})
        public String  updateUserPost(@ModelAttribute User user) {
            System.out.println(user);
            userServiceImpl.updateUser(user);
            return "redirect:/admin/users";
        }


    @GetMapping(value = "/users/delete/{id}")
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
