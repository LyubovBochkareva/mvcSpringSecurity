package spring.dao.impl;

import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;

@Transactional
public abstract class AbstractDao<PK extends Serializable, T> {

    @PersistenceContext
    EntityManager entityManager;

    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    AbstractDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @SuppressWarnings("unchecked")
    public T getEntityByKey(PK key) {
        return entityManager.find(persistentClass, key);
    }

    public void update(T entity) {
        entityManager.merge(entity);
    }

    public List<T> getAllEntity() {
        String genericClassName = persistentClass.toGenericString();
        genericClassName = genericClassName.substring(genericClassName.lastIndexOf('.') + 1);
        String hql = "FROM " + genericClassName;
        return entityManager.createQuery(hql).getResultList();
    }

    public void persist(T entity) {
        entityManager.persist(entity);
    }

    public void deleteByKey(PK pk) {
        T entity = entityManager.find(persistentClass, pk);
        entityManager.remove(entity);
    }

    protected Session getSession() {
        return (Session) entityManager.getDelegate();
    }
}
