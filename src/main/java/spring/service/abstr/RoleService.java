package spring.service.abstr;

import spring.dto.RoleDTO;
import spring.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    void addRole(Role role);

    RoleDTO getRoleByRoleName(String roleName);

    Role getRoleById(Long id);

    List<RoleDTO> getAllRoles();

    void updateRoles(Role role);

    void deleteRoleById(Long id);
}
