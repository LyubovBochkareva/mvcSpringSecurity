package spring.dao.abstr;

import spring.model.Role;

public interface RoleDao extends GenericDao<Long, Role> {

    Role getRoleByRoleName(String roleName);
}
