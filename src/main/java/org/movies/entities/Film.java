package org.movies.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.movies.repositories.StrangeConverter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "film", schema = "movies")
@Builder
@Getter
@Setter
@AllArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id", columnDefinition = "SMALLINT")
    private Integer id;

    @Column(name = "title", length = 128)
    private String title;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "release_year", columnDefinition = "DATE")
    private Date releaseYear;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @ManyToOne
    @JoinColumn(name = "original_language_id")
    private Language originalLanguage;

    @Column(name = "rental_duration", columnDefinition = "tinyint")
    private Integer rentalDuration;

    @Column(name = "rental_rate", columnDefinition = "decimal")
    private double rentalRate;

    @Column(name = "length", columnDefinition = "SMALLINT")
    private Integer length;

    @Column(name = "replacement_cost", columnDefinition = "decimal")
    private double replacementCost;

    @Enumerated(EnumType.STRING)
    @Column(name = "rating", columnDefinition = "enum('G', 'PG', 'PG-13', 'R', 'NC-17')")
    private Rating rating;

    @Convert(converter = StrangeConverter.class)
    @Column(name = "special_features")
    private Set<SpecialFeature> specialFeatures;

    @ManyToMany
    @JoinTable(name = "film_category",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @ManyToMany
    @JoinTable(name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private List<Actor> actors;

    @UpdateTimestamp
    @Column(name = "last_update")
    private Timestamp lastUpdate;

    public Film() {

    }
}
