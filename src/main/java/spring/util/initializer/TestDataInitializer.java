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
        Role roleAdmin = new Role();
        roleAdmin.setName("ADMIN");
        Role roleUser = new Role();
        roleUser.setName("USER");

//		<---Adding roles into a DB--->
        roleService.addRole(roleAdmin);
        roleService.addRole(roleUser);

//		<---Creating users--->
        Set<Role> roleListAdmin = new HashSet();
        roleListAdmin.add(roleUser);
        roleListAdmin.add(roleAdmin);
        User admin = new User();
        admin.setUsername("one");
        admin.setPassword("$2a$10$87JLFpwqxEmQNwkO1QQcLul25EpM8Nm2Vvb4esmnMIZmvRpvA9kDO");
        admin.setName("one");
        admin.setAge(1);
        admin.setRoles(roleListAdmin);

//		<---Adding users into a DB--->
        userService.addUser(admin);
    }
}
