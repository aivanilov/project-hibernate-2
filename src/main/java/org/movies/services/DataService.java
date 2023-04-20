package org.movies.services;

import jakarta.persistence.Entity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.movies.entities.*;
import org.movies.repositories.BasicRepository;
import org.movies.repositories.CityRepository;
import org.movies.repositories.CustomerRepository;
import org.reflections.Reflections;

import jakarta.transaction.Transactional;
import java.util.List;

@Transactional(rollbackOn = {Exception.class})
public class DataService {

    public final SessionFactory sessionFactory;

    public final BasicRepository<Actor, Integer> actorDAO;
    public final BasicRepository<Address, Integer> addressDAO;
    public final BasicRepository<Category, Integer> categoryDAO;
    public final CityRepository cityDAO;
    public final BasicRepository<Country, Integer> countryDAO;
    public final CustomerRepository customerDAO;
    public final BasicRepository<Film, Integer> filmDAO;
    public final BasicRepository<Inventory, Integer> inventoryDAO;
    public final BasicRepository<Language, Integer> languageDAO;
    public final BasicRepository<Payment, Integer> paymentDAO;
    public final BasicRepository<Rental, Integer> rentalDAO;
    public final BasicRepository<Staff, Integer> staffDAO;
    public final BasicRepository<Store, Integer> storeDAO;

    public DataService() {
        Configuration cfg = new Configuration();
        cfg.configure();
        Reflections reflections = new Reflections("org.movies");

        for (Class<?> clazz : reflections.getTypesAnnotatedWith(Entity.class)) {
            cfg.addAnnotatedClass(clazz);
        }

        sessionFactory = cfg.buildSessionFactory();
        actorDAO = new BasicRepository<>(Actor.class, sessionFactory);
        addressDAO = new BasicRepository<>(Address.class, sessionFactory);
        categoryDAO = new BasicRepository<>(Category.class, sessionFactory);
        cityDAO = new CityRepository(sessionFactory);
        countryDAO = new BasicRepository<>(Country.class, sessionFactory);
        customerDAO = new CustomerRepository(sessionFactory);
        filmDAO = new BasicRepository<>(Film.class, sessionFactory);
        inventoryDAO = new BasicRepository<>(Inventory.class, sessionFactory);
        languageDAO = new BasicRepository<>(Language.class, sessionFactory);
        paymentDAO = new BasicRepository<>(Payment.class, sessionFactory);
        rentalDAO = new BasicRepository<>(Rental.class, sessionFactory);
        staffDAO = new BasicRepository<>(Staff.class, sessionFactory);
        storeDAO = new BasicRepository<>(Store.class, sessionFactory);
    }

    public List<Inventory> getAvailableFilms() {
        List<Inventory> availableInventory;
        Session session = sessionFactory.openSession();
        try (session) {
            Transaction tx = session.getTransaction();
            try {
                tx.begin();
                String sql = """
                                SELECT DISTINCT i.inventory_id, i.film_id, i.store_id, i.last_update
                                FROM movies.inventory i
                                    LEFT JOIN movies.rental r on i.inventory_id = r.inventory_id
                                    JOIN movies.film f on i.film_id = f.film_id
                                WHERE r.return_date IS NOT NULL
                                   OR r.rental_id IS NULL
                                   """;
                NativeQuery<Inventory> nativeQuery = session.createNativeQuery(sql, Inventory.class);
                availableInventory = nativeQuery.getResultList();
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new RuntimeException(e);
            }
        }
        return availableInventory;
    }
}
