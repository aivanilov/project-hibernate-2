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
@Table(name = "payment", schema = "movies")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", columnDefinition = "smallint")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(targetEntity = Staff.class)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne(targetEntity = Rental.class)
    @JoinColumn(name = "rental_id")
    private Rental rental;

    @Column(name = "amount", columnDefinition = "decimal")
    private double amount;

    @Column(name = "payment_date", columnDefinition = "datetime")
    private LocalDateTime paymentDate;

    @UpdateTimestamp
    @Column(name = "last_update")
    private Timestamp lastUpdate;

    public Payment() {

    }
}
