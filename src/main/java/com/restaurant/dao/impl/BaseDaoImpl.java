package com.restaurant.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Transactional
public abstract class BaseDaoImpl<T> {

    @Autowired
    private SessionFactory sessionFactory;

    private Class<T> clazz;

    @SuppressWarnings("unchecked")
    public BaseDaoImpl() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.clazz = (Class<T>) type.getActualTypeArguments()[0];
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void save(T entity) {
        getSession().save(entity);
    }

    public void update(T entity) {
        getSession().update(entity);
    }

    public void delete(Long id) {
        T entity = getById(id);
        if (entity != null) {
            getSession().delete(entity);
        }
    }

    public T getById(Long id) {
        return getSession().get(clazz, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return getSession().createQuery("from " + clazz.getSimpleName()).list();
    }
}
