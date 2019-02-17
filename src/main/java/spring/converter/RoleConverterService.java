package spring.converter;

import spring.dto.RoleDTO;
import spring.model.Role;

import java.util.Set;

public interface RoleConverterService {

    RoleDTO getRoleByEntity(Role role);

    Set<RoleDTO> getRoleByEntity(Set<Role> role);

    Role getRoleByRoleDTO(RoleDTO roleDTO);

    Set<Role> getRoleByRoleDTO(Set<RoleDTO> roleDTO);
}
