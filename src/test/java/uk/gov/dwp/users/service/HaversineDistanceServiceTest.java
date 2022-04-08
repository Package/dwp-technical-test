package uk.gov.dwp.users.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.dwp.users.domain.Coordinate;
import uk.gov.dwp.users.domain.Location;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HaversineDistanceServiceTest {

    private DistanceService underTest;

    private static Stream<Arguments> provideLocationsAndDistance() {
        return Stream.of(
                Arguments.of(Location.LONDON, Location.LONDON, 0.0),
                Arguments.of(Location.MANCHESTER, Location.LONDON, 163.0),
                Arguments.of(Location.NEWCASTLE, Location.LONDON, 248.0),
                Arguments.of(Location.BLACKPOOL, Location.MANCHESTER, 40.0)
        );
    }

    @BeforeEach
    void setUp() {
        this.underTest = new HaversineDistanceService();
    }

    @ParameterizedTest
    @MethodSource("provideLocationsAndDistance")
    void calculatesApproximateHaversineDistance_GivenTwoLocations(Location startLocation, Location endLocation,
                                                                  double approximateHaversineDistance) {
        Coordinate start = startLocation.getCoordinates();
        Coordinate end = endLocation.getCoordinates();

        double distanceResult = underTest.distanceBetweenCoordinatesInMiles(start, end);

        assertEquals(approximateHaversineDistance, distanceResult);
    }
}