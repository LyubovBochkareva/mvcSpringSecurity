package spring.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import spring.service.abstr.UserService;
import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "/admin")
@Controller
public class AdminController {

    private final UserService userServiceImpl;

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
    public ModelAndView updateGet(@PathVariable("id") Long id) {
        User user = userServiceImpl.getUserById(id);
        ModelAndView modelAndView = new ModelAndView("updateUser");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping(value = "/users/{id}/update")
    public String updatePost(@PathVariable("id") Long id, User user) {
        user.setId(id);
        userServiceImpl.updateUser(user);
        return "redirect:/admin/users";
    }


    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public ModelAndView deleteUser(HttpServletRequest request) {
        Long userId = Long.valueOf(request.getParameter("id"));
        userServiceImpl.deleteUser(userId);
        return new ModelAndView("redirect:/admin");
    }



    @RequestMapping(value = "/user", method = RequestMethod.GET)
        public ModelAndView user(ModelAndView modelAndView){
        modelAndView.setViewName("user");
        return modelAndView;
    }

}
