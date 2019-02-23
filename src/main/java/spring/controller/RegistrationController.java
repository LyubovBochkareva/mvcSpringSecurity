package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import spring.dto.RoleDTO;
import spring.dto.UserDTO;
import spring.model.Role;
import spring.model.User;
import spring.service.abstr.RoleService;
import spring.service.abstr.UserService;
import spring.util.validation.PasswordValidator;


import javax.validation.Valid;
import java.util.*;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView saveNewUser() {
        ModelAndView model = new ModelAndView("registration");
        model.addObject("user", new UserDTO());
        return model;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView getRegistrationForm(@RequestParam(value = "confirmPassword") String confirmPassword,
                                            @Valid UserDTO userFromPage, BindingResult bindingResult) {

        ModelAndView model = new ModelAndView("registration");
        model.addObject("user", userFromPage);

        boolean isConfEqualToPass =
                PasswordValidator.validatePassword(userFromPage.getPassword(), confirmPassword);

        if (bindingResult.hasErrors()) {
            return model;
        } else if (!isConfEqualToPass) {
            model.addObject("errorConfirm", "Error confirm pass");
            return model;
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userFromPage.setPassword(passwordEncoder.encode(userFromPage.getPassword()));


        String username = userFromPage.getUsername();
        boolean isUser = userService.getUserByLogin(username) != null;

        if (isUser) {
            model.addObject("errorUsername", "Duplicate email");
            return model;
        } else {
            RoleDTO role = roleService.getRoleByRoleName("USER");
            List<RoleDTO> roles = new ArrayList<>();
            roles.add(role);
            userFromPage.setRoles(roles);
            userService.addUser(userFromPage);
            model.addObject("congratulations", "registration completed successfully");
        }
        return model;
    }
}

