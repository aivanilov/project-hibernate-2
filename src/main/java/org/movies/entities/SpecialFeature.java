package org.movies.entities;

import lombok.Getter;

@Getter
public enum SpecialFeature {
    TRAILERS("Trailers"),
    COMMENTARIES("Commentaries"),
    DELETED_SCENES("Deleted Scenes"),
    BEHIND_THE_SCENES("Behind the Scenes");

    private final String name;

    SpecialFeature(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SpecialFeature getFeatureByValue(String value) {
        if(value == null || value.isEmpty()) {
            return null;
        }

        SpecialFeature[] values = SpecialFeature.values();
        for (SpecialFeature feature : values) {
            if (feature.getName().equals(value)) {
                return feature;
            }
        }

        return null;
    }
}
