package org.movies.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.movies.entities.Rating;

@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, String> {
    @Override
    public String convertToDatabaseColumn(Rating attribute) {
        return attribute.getStringValue();
    }

    @Override
    public Rating convertToEntityAttribute(String dbData) {
        Rating[] values = Rating.values();

        for (Rating rating : values) {
            if (rating.getStringValue().equals(dbData)) {
                return rating;
            }
        }
        return null;
    }
}
