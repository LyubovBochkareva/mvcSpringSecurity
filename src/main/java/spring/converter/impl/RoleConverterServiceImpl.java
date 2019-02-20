package spring.converter.impl;

import org.springframework.stereotype.Service;
import spring.converter.RoleConverterService;
import spring.dto.RoleDTO;
import spring.model.Role;

import java.util.*;

@Service
public class RoleConverterServiceImpl implements RoleConverterService {


    @Override
    public RoleDTO getRoleByEntity(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(String.valueOf(role.getId()));
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    @Override
    public List<RoleDTO> getRoleByEntity(List<Role> roleSet) {
        List<RoleDTO> roleDTOSet = new ArrayList<>();
        for(Role role: roleSet) {
            RoleDTO roleDTO = getRoleByEntity(role);
            roleDTOSet.add(roleDTO);
        }
        return roleDTOSet;
    }

    @Override
    public Role getRoleByRoleDTO(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(Long.valueOf(roleDTO.getId()));
        role.setName(roleDTO.getName());
        return role;
    }

    @Override
    public List<Role> getRoleByRoleDTO(List<RoleDTO> roleDTOSet) {
        List<Role> roleSet = new ArrayList<>();
        for(RoleDTO roleDTO:roleDTOSet) {
            Role role = getRoleByRoleDTO(roleDTO);
            roleSet.add(role);
        }
        return roleSet;
    }
}
