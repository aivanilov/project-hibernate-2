package org.movies.repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class BasicRepository<T, K> implements Repository<T, K>{

    private final SessionFactory sessionFactory;
    private final Class<T> tClass;

    public BasicRepository(Class<T> tClass, SessionFactory sessionFactory) {
        this.tClass = tClass;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public T save(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.getTransaction();
            try {
                tx.begin();
                session.persist(entity);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new RuntimeException(e);
            }
        }
        return entity;
    }

    @Override
    public T update(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.getTransaction();
            try {
                tx.begin();
                session.merge(entity);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new RuntimeException(e);
            }
        }
        return entity;
    }

    @Override
    public boolean deleteById(K id) {
        boolean wasDeleted = false;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.getTransaction();
            try {
                tx.begin();
                Optional<T> object = findById(id);

                if (object.isPresent()) {
                    session.remove(object.get());
                    wasDeleted = true;
                }

                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new RuntimeException(e);
            }
        }
        return wasDeleted;
    }

    @Override
    public Optional<T> findById(K id) {
        Optional<T> found = Optional.empty();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.getTransaction();
            try {
                tx.begin();
                found = Optional.of(session.get(tClass, id));
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new RuntimeException(e);
            }
        }
        return found;
    }

    @Override
    public List<T> findAll() {
        List<T> found = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.getTransaction();
            try {
                tx.begin();
                String hql = String.format("FROM %s", tClass.getName());
                Query<T> query = session.createQuery(hql, tClass);
                found = query.getResultList();
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new RuntimeException(e);
            }
        }
        return found;
    }

    @Override
    public long count() {
        long count = 0;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.getTransaction();
            try {
                tx.begin();
                CriteriaBuilder cb = session.getCriteriaBuilder();
                CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
                Root<T> root = criteriaQuery.from(tClass);
                criteriaQuery.select(cb.count(root));
                count = session.createQuery(criteriaQuery).getSingleResult();
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new RuntimeException(e);
            }
        }

        return count;
    }

    @Override
    public boolean existsById(K id) {
        return findById(id).isPresent();
    }
}


