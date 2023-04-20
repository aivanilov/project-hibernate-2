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
@Table(name = "language", schema = "movies")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id", columnDefinition = "tinyint")
    private Integer id;

    @Column(name = "name", columnDefinition = "char", length = 20)
    private String name;

    @UpdateTimestamp
    @Column(name = "last_update")
    private Timestamp lastUpdate;

    public Language() {

    }
}
