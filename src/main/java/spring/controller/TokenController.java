package spring.controller;

import com.github.scribejava.apis.VkontakteApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import spring.model.Role;
import spring.model.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class TokenController {
    private final String clientId = "6899904";
    private final String clientSecret = "5p06e04rKTQq8t25mlKZ";
    private final OAuth20Service service = new ServiceBuilder(clientId)
            .apiSecret(clientSecret)
            .scope("email") // replace with desired scope
            .callback("http://localhost:8081/callback/")
            .build(VkontakteApi.instance());

    private static final String PROTECTED_RESOURCE_URL = "https://api.vk.com/method/users.get?v="
            + VkontakteApi.VERSION;

    @RequestMapping(value = "/callback/", method = RequestMethod.GET)
    public void tokenValue (HttpServletResponse response, @RequestParam(value = "code") String code) throws InterruptedException, ExecutionException, IOException, ParseException {
        final OAuth2AccessToken accessToken = service.getAccessToken(code);
        final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, request);
        final Response responseVK = service.execute(request);
        String responseForJSON = responseVK.getBody();
        Object obj = new JSONParser().parse(responseForJSON);
        JSONObject jo = (JSONObject) obj;
        String username = (String) jo.get("id");
        String name = (String) jo.get("first_name");
        User user = new User();
        user.setUsername(username);
        user.setPassword("vk" + username);
        user.setName(name);
        user.setAge(0);
        List<Role> roleListUser = new LinkedList<>();
        Role roleUser = new Role();
        roleUser.setName("USER");
        roleListUser.add(roleUser);
        user.setRoles(roleListUser);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword(),
                        user.getAuthorities()));
        response.sendRedirect("/user");
    }
}
