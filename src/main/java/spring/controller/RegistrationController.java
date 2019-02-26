package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import spring.dto.UserDTO;
import spring.service.abstr.UserService;


import javax.validation.Valid;


@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView saveNewUser() {
        ModelAndView model = new ModelAndView("registration");
        model.addObject("user", new UserDTO());
        return model;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView getRegistrationForm(@RequestParam(value = "confirmPassword") String confirmPassword,
                                            @Valid UserDTO userFromPage, BindingResult bindingResult) {

        return userService.getRegistrationForm(userFromPage,confirmPassword,bindingResult);
    }
}

