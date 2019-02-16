package spring.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.dao.abstr.RoleDao;
import spring.model.Role;

import java.util.Set;

@Transactional
@Repository
public class RoleDaoImpl extends AbstractDao<Long, Role> implements RoleDao {

    public Role getRoleByRoleName(String name) {
        return (Role) getSession().createQuery("FROM Role WHERE name = :name").setParameter("name", name).uniqueResult();
    }
}
