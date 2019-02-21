package spring.service.impl;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.converter.RoleConverterService;
import spring.dao.abstr.RoleDao;
import spring.dto.RoleDTO;
import spring.model.Role;
import spring.service.abstr.RoleService;
import spring.service.exceptions.NotFoundException;
import spring.dao.impl.exceptions.PersistException;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final static Logger logger = Logger.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleConverterService roleConverterService;

    public void addRole(Role role) {
        try {
            roleDao.persist(role);
            logger.info("Added : " + role);
        } catch (HibernateException e) {
            logger.error("Failed to add an role " + role);
            throw new PersistException("Failed to add an role", e);
        }
    }

    public Role getRoleByRoleName(String roleName) {
        Role roleFromDB = roleDao.getRoleByRoleName(roleName);

        if (roleFromDB == null) {
            throw new NotFoundException("The role is not found.");
        }
        return roleFromDB;
    }

    public Role getRoleById(Long id) {
        return roleDao.getEntityByKey(id);
    }

    public List<RoleDTO> getAllRoles() {
        return roleConverterService.getRoleByEntity(roleDao.getAllEntity());
    }

    public void updateRoles(Role role) {
        try {
            roleDao.update(role);
            logger.info("Update : " + role);
        } catch (HibernateException e) {
            logger.error("Failed to update an role " + role);
        }
    }

    public void deleteRoleById(Long id) {
        try {
            roleDao.deleteByKey(id);
            logger.info("Deleted role id=" + id);
        } catch (HibernateException e) {
            logger.error("Failed to deleted an role id=" + id);
        }
    }

}
