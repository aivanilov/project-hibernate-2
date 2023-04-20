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
@Table(name = "category", schema = "movies")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", columnDefinition = "tinyint")
    private int id;

    @Column(name = "name", length = 25)
    private String name;

    @UpdateTimestamp
    @Column(name = "last_update")
    private Timestamp lastUpdate;

    @ManyToMany(mappedBy = "categories")
    private List<Film> films;

    public Category() {

    }
}
