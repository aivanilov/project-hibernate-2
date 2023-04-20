package org.movies.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "rental", schema = "movies")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private int id;

    @Column(name = "rental_date", columnDefinition = "datetime")
    private LocalDateTime dateTime;

    @ManyToOne(targetEntity = Inventory.class)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "return_date", columnDefinition = "datetime")
    private LocalDateTime returnDate;

    @ManyToOne(targetEntity = Staff.class)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @UpdateTimestamp
    @Column(name = "last_update")
    private Timestamp lastUpdate;

    public Rental() {
    }
}
