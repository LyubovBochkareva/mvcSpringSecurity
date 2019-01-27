package spring.service.abstr;

import spring.model.Role;

import java.util.List;

public interface RoleService {

    void addRole(Role role);

    Role getRoleByRoleName(String roleName);

    Role getRoleById(Long id);

    List<Role> getAllRoles();

    void updateRoles(Role role);

    void deleteRoleById(Long id);
}
