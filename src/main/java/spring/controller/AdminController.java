package spring.controller;

import com.google.gson.Gson;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.model.Role;
import spring.model.User;
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
        checkRoles(user);
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
        modelAndView.addObject("allRoles", roleService.getAllRoles());
        return modelAndView;
    }

    @PostMapping(value = "/users/add")
    public String addUserPost(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        checkRoles(user);
        userServiceImpl.addUser(user);
        return "redirect:/admin/users";
    }


    @RequestMapping(value = "/user", method = RequestMethod.GET)
        public ModelAndView user(ModelAndView modelAndView){
        modelAndView.setViewName("user");
        return modelAndView;
    }

    private void checkRoles(User user){
        if (user.getRoles().contains(new Role("ADMIN"))){
            Role adminRole = roleService.getRoleByRoleName("ADMIN");
            user.getRoles().remove(new Role("ADMIN", null));
            user.getRoles().add(adminRole);
        }
        if (user.getRoles().contains((new Role("USER")))){
            Role userRole = roleService.getRoleByRoleName("USER");
            user.getRoles().remove(new Role("USER", null));
            user.getRoles().add(userRole);
        }
    }

}
