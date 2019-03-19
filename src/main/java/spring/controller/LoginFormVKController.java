package spring.controller;

import com.github.scribejava.apis.VkontakteApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginFormVKController {

    private final String clientId = "6899904";
    private final String clientSecret = "5p06e04rKTQq8t25mlKZ";
    private final OAuth20Service service = new ServiceBuilder(clientId)
            .apiSecret(clientSecret)
            .scope("email") // replace with desired scope
            .responseType("code")
            .callback("http://localhost:8081/callback/")
            .build(VkontakteApi.instance());

    @RequestMapping(value = "/autorization", method = RequestMethod.GET)
    public String getLoginForm() {
        // Obtain the Authorization URL
        System.out.println("Fetching the Authorization URL...");
        final String authorizationUrl = service.getAuthorizationUrl();
        return "redirect:" + authorizationUrl;
    }
}
