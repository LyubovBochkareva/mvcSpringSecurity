package spring.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import spring.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {

    private final UserService userServiceImpl;

    @Autowired
    public UserController(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error",required = false) String error,
                                  @RequestParam(value = "logout",	required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid Credentials provided.");
        }

        if (logout != null) {
            model.addObject("message", "Logged out from JournalDEV successfully.");
        }

        model.setViewName("login");
        return model;
    }
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView getAllUsers(ModelAndView model){
        List<User> userList = userServiceImpl.getAllUser();
        model.addObject("listUsers", userList);
        model.setViewName("admin");
        return model;
    }


    @RequestMapping(value = "/editUser", method = RequestMethod.GET)
    public ModelAndView update(HttpServletRequest request) {
        Long userId = Long.valueOf(request.getParameter("id"));
        User user = userServiceImpl.getUserById(userId);
        ModelAndView model = new ModelAndView("UserForm");
        model.addObject("user", user);
        return model;
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public ModelAndView deleteUser(HttpServletRequest request) {
        Long userId = Long.valueOf(request.getParameter("id"));
        userServiceImpl.deleteUser(userId);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public ModelAndView insertUser(ModelAndView model) {
        User user = new User();
        model.addObject("user", user);
        model.setViewName("UserForm");
        return model;
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public ModelAndView saveUser(@ModelAttribute("UserForm") User user) {
        userServiceImpl.saveOrUpdate(user);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
        public ModelAndView user(ModelAndView modelAndView){
        modelAndView.setViewName("user");
        return modelAndView;
    }

    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        return "accessDenied";
    }

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

}
