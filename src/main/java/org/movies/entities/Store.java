package org.movies.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "store", schema = "movies")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", columnDefinition = "tinyint")
    private Integer id;

    @ManyToOne(targetEntity = Staff.class)
    @JoinColumn(name = "manager_staff_id")
    private Staff manager;

    @ManyToOne(targetEntity = Address.class)
    @JoinColumn(name = "address_id")
    private Address address;

    @UpdateTimestamp
    @Column(name = "last_update")
    private Timestamp lastUpdate;

    public Store() {
    }
}
