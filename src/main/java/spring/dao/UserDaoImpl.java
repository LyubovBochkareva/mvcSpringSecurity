package spring.dao;

import spring.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUserByLogin(String login) {
        Query query = entityManager.createQuery("FROM User WHERE login =:userLogin")
                .setParameter("userLogin", login);
        return (User) query.getSingleResult();
    }


    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<User> getAllUser(){
        Query query = entityManager.createQuery("FROM User");
        List<User> userList = query.getResultList();
        return userList;
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        entityManager.merge(user);
        return user;
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        User user = (User) entityManager.createQuery("FROM User WHERE id =:userId")
                .setParameter("userId", id)
                .getSingleResult();
        entityManager.remove(user);
    }

    @Transactional
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Transactional
    public void saveOrUpdate(User user) {
        entityManager.merge(user);
    }
}

