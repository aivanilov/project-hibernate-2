package org.movies.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.movies.entities.*;
import org.movies.utils.Strings;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

class CustomerServiceTest {
    private final DataService ds = new DataService();

    @Test
    void createCustomer() {
        Session session = ds.sessionFactory.openSession();
        try (session) {
            Transaction tx = session.getTransaction();
            try {
                tx.begin();
                City city = ds.cityDAO.getByName("Lipetsk");

                Address address = ds.addressDAO.save(Address.builder()
                        .address("Line1")
                        .address2("Line2")
                        .phone("+79091234567")
                        .district("Columbia")
                        .postalCode("143965")
                        .city(city)
                        .build());

                Optional<Store> store = ds.storeDAO.findById(1);

                Customer customer = ds.customerDAO.save(Customer.builder()
                        .active(true)
                        .firstName("Аристарх")
                        .lastName("Козлов")
                        .address(address)
                        .email("email@email.com")
                        .store(store.orElse(null))
                        .build());

                Assertions.assertNotNull(customer.getId());
                Assertions.assertEquals("Line2", address.getAddress2());
                Assertions.assertTrue(ds.customerDAO.deleteById(customer.getId()));
                Assertions.assertTrue(ds.addressDAO.deleteById(address.getId()));
            } catch (Exception e) {
                tx.rollback();
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    void returnFilm() {
        Session session = ds.sessionFactory.openSession();
        try (session) {
            Transaction tx = session.getTransaction();
            try {
                tx.begin();
                Optional<Rental> optional = ds.rentalDAO.findById(11541);

                if (optional.isPresent()) {
                    Rental rental = optional.get();
                    LocalDateTime localDateTime = LocalDateTime.now();
                    rental.setReturnDate(localDateTime);
                    rental = ds.rentalDAO.update(rental);
                    Assertions.assertEquals(localDateTime, rental.getReturnDate());
                    rental.setReturnDate(null);
                    rental = ds.rentalDAO.update(rental);
                    Assertions.assertNull(rental.getReturnDate());
                }

                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    void rentFilm() {
        Session session = ds.sessionFactory.openSession();
        try (session) {
            Transaction tx = session.getTransaction();
            try {
                tx.begin();
                Customer customer = ds.customerDAO.getByName("SUSAN", "WILSON");
                List<Inventory> availableFilms = ds.getAvailableFilms();
                int rand = ThreadLocalRandom.current().nextInt(0, availableFilms.size());
                Inventory inventory = availableFilms.get(rand);
                Staff staff = ds.staffDAO.findById(2).orElse(null);

                Rental rental = ds.rentalDAO.save(Rental.builder()
                        .customer(customer)
                        .inventory(inventory)
                        .rentalDate(LocalDateTime.now())
                        .staff(staff)
                        .build());

                Payment payment = ds.paymentDAO.save(Payment.builder()
                        .amount(1.99)
                        .customer(customer)
                        .paymentDate(LocalDateTime.now())
                        .staff(staff)
                        .rental(rental)
                        .build());

                Assertions.assertNotNull(payment.getId());
                Assertions.assertEquals(1.99, payment.getAmount());
                Assertions.assertTrue(ds.paymentDAO.deleteById(payment.getId()));
                Assertions.assertTrue(ds.rentalDAO.deleteById(rental.getId()));

                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    void createNewFilm() {
        Session session = ds.sessionFactory.openSession();
        try (session) {
            Transaction tx = session.getTransaction();
            try {
                tx.begin();
                List<Actor> allActors = ds.actorDAO.findAll();
                List<Category> allCategories = ds.categoryDAO.findAll();
                List<Actor> actors = allActors.stream().limit(3).collect(Collectors.toList());
                List<Category> categories = allCategories.stream().limit(2).collect(Collectors.toList());
                String specialFeatures = SpecialFeature.BEHIND_THE_SCENES.getName() + Strings.COMMA + SpecialFeature.DELETED_SCENES.getName();

                Film film = ds.filmDAO.save(Film.builder()
                        .actors(actors)
                        .categories(categories)
                        .title("Whatever film")
                        .description("New film for testing issues")
                        .rating(Rating.PG_13)
                        .language(ds.languageDAO.findById(1).orElse(null))
                        .length(120)
                        .originalLanguage(ds.languageDAO.findById(1).orElse(null))
                        .releaseYear(Year.of(2023))
                        .rentalDuration(5)
                        .rentalRate(3.2)
                        .replacementCost(19.99)
                        .specialFeatures(specialFeatures)
                        .build());

                Assertions.assertNotNull(film.getId());
                Assertions.assertTrue(ds.filmDAO.deleteById(film.getId()));

                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new RuntimeException(e);
            }
        }
    }
}