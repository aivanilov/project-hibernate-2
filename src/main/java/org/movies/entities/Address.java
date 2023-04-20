package org.movies.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "address", schema = "movies")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", columnDefinition = "smallint")
    private int id;

    @Column(name = "address", length = 50)
    private String address;
    @Column(name = "address2", length = 50)
    private String address2;

    @Column(name = "district", length = 50)
    private String district;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @Column(name = "phone", length = 20)
    private String phone;

    @UpdateTimestamp
    @Column(name = "last_update")
    private Timestamp lastUpdate;

    public Address() {
    }
}
