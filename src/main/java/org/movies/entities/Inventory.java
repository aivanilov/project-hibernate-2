package org.movies.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "inventory", schema = "movies")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private int id;

    @ManyToOne(targetEntity = Film.class)
    @JoinColumn(name = "film_id")
    private Film film;

    @ManyToOne(targetEntity = Store.class)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany
    @JoinColumn(name = "rental_id")
    private List<Rental> rental;

    @UpdateTimestamp
    @Column(name = "last_update")
    private Timestamp lastUpdate;

    public Inventory() {

    }
}
