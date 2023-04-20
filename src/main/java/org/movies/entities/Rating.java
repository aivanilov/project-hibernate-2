package org.movies.entities;

import lombok.Getter;

@Getter
public enum Rating {
    G("G"),
    PG("PG"),
    PG_13("PG-13"),
    R("R"),
    NC_17("NC-17");

    private final String stringValue;

    Rating(String stringValue) {
        this.stringValue = stringValue;
    }
}
