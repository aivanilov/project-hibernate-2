package org.movies.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.movies.entities.City;
import org.movies.entities.Customer;

public class CustomerRepository extends BasicRepository<Customer, Integer> {
    public CustomerRepository(SessionFactory sessionFactory) {
        super(Customer.class, sessionFactory);
    }

    public Customer getByName(String firstName, String lastName) {
        Customer customer = null;
        String name = firstName + lastName;
        try (Session session = getSessionFactory().openSession()) {
            Transaction tx = session.getTransaction();
            try {
                tx.begin();
                String hql = "select c from Customer c where CONCAT(c.firstName, c.lastName) = :name";
                Query<Customer> query = session.createQuery(hql, Customer.class)
                        .setParameter("name", name);
                customer = query.getSingleResult();
            } catch (Exception e) {
                tx.rollback();
                throw new RuntimeException(e);
            }
        }
        return customer;
    }
}
