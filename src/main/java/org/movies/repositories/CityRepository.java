package org.movies.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.movies.entities.City;

import java.util.Optional;

public class CityRepository extends BasicRepository<City, Integer>  {
    public CityRepository(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }

    public City getByName(String cityName) {
        City city = null;
        try (Session session = getSessionFactory().openSession()) {
            Transaction tx = session.getTransaction();
            try {
                tx.begin();
                String hql = "select c from City c where c.name = :cityName";
                Query<City> query = session.createQuery(hql, City.class)
                        .setParameter("cityName", cityName);
                city = query.getSingleResult();
            } catch (Exception e) {
                tx.rollback();
                throw new RuntimeException(e);
            }
        }
        return city;
    }
}
