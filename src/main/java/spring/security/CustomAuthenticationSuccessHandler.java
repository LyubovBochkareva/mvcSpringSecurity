package spring.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import spring.converter.RoleConverterService;
import spring.model.Role;
import spring.service.abstr.RoleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Service
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final static Logger logger = Logger.getLogger(CustomAuthenticationSuccessHandler.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleConverterService roleConverterService;
    private Role roleAdmin;
    private Role roleUser;
    private Role roleRestClient;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    private void handle(HttpServletRequest request,
                        HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private String determineTargetUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.contains(getAdminRole())) {
            return "/admin";
        } else if (authorities.contains(getUserRole())) {
            return "/user";
        } else if (authorities.contains(getRestClientRole())) {
            return "/api/users/";
        } else {
            throw new IllegalStateException();
        }
    }

    private Role getAdminRole() {
        if (roleAdmin == null) {
            roleAdmin = roleConverterService.getRoleByRoleDTO(roleService.getRoleByRoleName("ADMIN"));
        }
        return roleAdmin;
    }

    private Role getUserRole() {
        if (roleUser == null) {
            roleUser = roleConverterService.getRoleByRoleDTO(roleService.getRoleByRoleName("USER"));
        }
        return roleUser;
    }

    private Role getRestClientRole() {
        if (roleRestClient == null) {
            roleRestClient = roleConverterService.getRoleByRoleDTO(roleService.getRoleByRoleName("RESTCLIENT"));
        }
        return roleRestClient;
    }
}
