package uk.gov.dwp.users.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Locale;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationFactory {

    public static Optional<Location> fromName(String locationName) {
        try {
            return Optional.of(Location.valueOf(locationName.toUpperCase(Locale.ROOT)));
        } catch (IllegalArgumentException exception) {
            return Optional.empty();
        }
    }
}
