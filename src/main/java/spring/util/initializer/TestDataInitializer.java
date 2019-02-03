package spring.util.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import spring.model.Role;
import spring.model.User;
import spring.service.abstr.UserService;
import spring.service.abstr.RoleService;

import java.util.HashSet;
import java.util.Set;

public class TestDataInitializer {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;


    private void init() {

//		<---Creating roles--->
        Role roleAdmin = new Role("ADMIN");
        Role roleUser = new Role("USER");

//		<---Adding roles into a DB--->
        roleService.addRole(roleAdmin);
        roleService.addRole(roleUser);

//		<---Creating users--->
        Set<Role> roleListAdmin = new HashSet();
        roleListAdmin.add(roleUser);
        roleListAdmin.add(roleAdmin);
        User admin = new User("one", "one", "one", 1, roleListAdmin);

//		<---Adding users into a DB--->
        userService.addUser(admin);
    }
}
