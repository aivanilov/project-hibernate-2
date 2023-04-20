package org.movies.repositories;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.movies.entities.SpecialFeature;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class StrangeConverter implements AttributeConverter<Set<SpecialFeature>, String> {
    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(Set<SpecialFeature> features) {
        if (features == null || features.isEmpty()) {
            return null;
        }
        return features.stream()
                .map(SpecialFeature::getName)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public Set<SpecialFeature> convertToEntityAttribute(String featuresAsString) {
        if (featuresAsString == null || featuresAsString.isEmpty()) {
            return Collections.emptySet();
        }
        return Arrays.stream(featuresAsString.split(DELIMITER))
                .map(String::trim)
                .map(SpecialFeature::valueOf)
                .collect(Collectors.toSet());
    }
}
