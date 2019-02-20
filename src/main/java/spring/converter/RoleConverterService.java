package spring.converter;

import spring.dto.RoleDTO;
import spring.model.Role;

import java.util.List;


public interface RoleConverterService {

    RoleDTO getRoleByEntity(Role role);

    List<RoleDTO> getRoleByEntity(List<Role> role);

    Role getRoleByRoleDTO(RoleDTO roleDTO);

    List<Role> getRoleByRoleDTO(List<RoleDTO> roleDTO);
}
