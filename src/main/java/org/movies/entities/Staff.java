package org.movies.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.descriptor.jdbc.LongVarbinaryJdbcType;

import java.sql.Blob;
import java.sql.Timestamp;

@Entity
@Table(name = "staff", schema = "movies")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id", columnDefinition = "tinyint")
    private Integer id;

    @Column(name = "first_name", length = 45)
    private String firstName;

    @Column(name = "last_name", length = 45)
    private String lastName;

    @ManyToOne(targetEntity = Address.class)
    @JoinColumn(name = "address_id")
    private Address address;

    @Lob
    @Column(name = "picture", columnDefinition = "BLOB")
    private byte[] picture;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "active", columnDefinition = "BOOLEAN")
    private boolean active;

    @Column(name = "username", length = 16)
    private String username;

    @Column(name = "password", length = 40)
    private String password;

    @UpdateTimestamp
    @Column(name = "last_update")
    private Timestamp lastUpdate;

    public Staff() {

    }
}
