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
@Table(name = "actor", schema = "movies")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id", columnDefinition = "smallint")
    private int id;

    @Column(name = "first_name", length = 45)
    private String firstName;

    @Column(name = "last_name", length = 45)
    private String lastName;

    @UpdateTimestamp
    @Column(name = "last_update")
    private Timestamp lastUpdate;

    @ManyToMany(mappedBy = "actors")
    private List<Film> films;

    public Actor() {
    }
}
