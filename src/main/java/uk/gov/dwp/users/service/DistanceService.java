package uk.gov.dwp.users.service;

import uk.gov.dwp.users.domain.Coordinate;

public interface DistanceService {
    double distanceBetweenCoordinatesInMiles(Coordinate start, Coordinate end);
}
