package spring.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import spring.service.abstr.RoleService;
import spring.service.abstr.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


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
        modelAndView.addObject("userFromPage", new UserDTO());
        modelAndView.addObject("allRoles", roleService.getAllRoles());
        return modelAndView;
    }


    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String addNewUser(UserDTO userFromPage) {
        userFromPage.setPassword(new BCryptPasswordEncoder().encode(userFromPage.getPassword()));
        userServiceImpl.addUser(userFromPage);
        return "redirect:/admin";
    }


    @RequestMapping(value = "/users/update/{id}", method = RequestMethod.POST)
    public String updateUserPost(@PathVariable("id") Long id, UserDTO userFromPage, HttpServletRequest request, HttpServletResponse response) {
        userServiceImpl.updateUser(userFromPage);
        if(userFromPage.getRoles().contains(roleService.getRoleByRoleName("ADMIN"))) {
            return "redirect:/admin";
        } else{
            HttpSession session;
            SecurityContextHolder.clearContext();
            session= request.getSession(false);
            if(session != null) {
                session.invalidate();
            }
            for(Cookie cookie : request.getCookies()) {
                cookie.setMaxAge(0);
            }
        } return "redirect:/login";
    }



    @GetMapping(value = "/users/delete/{id}")
    public String deleteUserGet(@PathVariable("id") Long id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/admin";
    }


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView user(ModelAndView modelAndView) {
        modelAndView.setViewName("user");
        return modelAndView;
    }

}
