package spring.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/user")
@Controller
public class UserController {

    @GetMapping()
    public ModelAndView getUserIndexPage() {
        return new ModelAndView("user");
    }
}
