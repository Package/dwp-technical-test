package uk.gov.dwp.users.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocationFactoryTest {

    @ParameterizedTest
    @ValueSource(strings = {"London", "Manchester", "Blackpool"})
    void fromName_ReturnsLocationOptional_GivenAValidLocationName(String locationName) {
        Optional<Location> location = LocationFactory.fromName(locationName);

        assertTrue(location.isPresent());
        assertEquals(locationName, location.get().getName());
    }


    @ParameterizedTest
    @ValueSource(strings = {"fake_location", "NOT A PLACE", ""})
    void fromName_ReturnsEmptyOptional_GivenAnInvalidLocationName(String locationName) {
        Optional<Location> location = LocationFactory.fromName(locationName);

        assertTrue(location.isEmpty());
    }
}