package spring.util.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import spring.converter.RoleConverterService;
import spring.converter.UserConverterService;
import spring.model.Role;
import spring.model.User;
import spring.service.abstr.UserService;
import spring.service.abstr.RoleService;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TestDataInitializer {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverterService userConverterService;

    
    private void init() {

//		<---Creating roles--->
        Role roleAdmin = new Role();
        roleAdmin.setName("ADMIN");
        Role roleUser = new Role();
        roleUser.setName("USER");
        Role roleRestClient = new Role();
        roleRestClient.setName("RESTCLIENT");

//		<---Adding roles into a DB--->
        roleService.addRole(roleAdmin);
        roleService.addRole(roleUser);
        roleService.addRole(roleRestClient);

//		<---Creating users--->
        List<Role> roleListAdmin = new LinkedList<>();
        roleListAdmin.add(roleUser);
        roleListAdmin.add(roleAdmin);
        User admin = new User();
        admin.setUsername("one");
        admin.setPassword("$2a$10$87JLFpwqxEmQNwkO1QQcLul25EpM8Nm2Vvb4esmnMIZmvRpvA9kDO");
        admin.setName("one");
        admin.setAge(1);
        admin.setRoles(roleListAdmin);

//		<---Adding users into a DB--->
        userService.addUser(userConverterService.getUserByEntity(admin));
    }
}
