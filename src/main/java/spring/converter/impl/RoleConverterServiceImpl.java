package spring.converter.impl;

import org.springframework.stereotype.Service;
import spring.converter.RoleConverterService;
import spring.dto.RoleDTO;
import spring.model.Role;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleConverterServiceImpl implements RoleConverterService {


    @Override
    public RoleDTO getRoleByEntity(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    @Override
    public Set<RoleDTO> getRoleByEntity(Set<Role> roleSet) {
        Set<RoleDTO> roleDTOSet = new HashSet<>();
        for(Role role: roleSet) {
            RoleDTO roleDTO = getRoleByEntity(role);
            roleDTOSet.add(roleDTO);
        }
        return roleDTOSet;
    }

    @Override
    public Role getRoleByRoleDTO(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setName(roleDTO.getName());
        return role;
    }

    @Override
    public Set<Role> getRoleByRoleDTO(Set<RoleDTO> roleDTOSet) {
        Set<Role> roleSet = new HashSet<>();
        for(RoleDTO roleDTO:roleDTOSet) {
            Role role = getRoleByRoleDTO(roleDTO);
            roleSet.add(role);
        }
        return roleSet;
    }
}
