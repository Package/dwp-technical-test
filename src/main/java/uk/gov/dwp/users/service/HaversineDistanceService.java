package uk.gov.dwp.users.service;

import org.springframework.stereotype.Service;
import uk.gov.dwp.users.domain.Coordinate;

@Service
public class HaversineDistanceService implements DistanceService {
    private static final int EARTH_RADIUS_IN_MILES = 3959;

    @Override
    public double distanceBetweenCoordinatesInMiles(Coordinate start, Coordinate end) {
        if (start.equals(end)) {
            return 0.0;
        }

        double haversinDistance = haversineFormula(
                start.getLatitude(), start.getLongitude(),
                end.getLatitude(), end.getLongitude()
        );

        return Math.round(haversinDistance * EARTH_RADIUS_IN_MILES);
    }

    private double haversineFormula(double startLat, double startLon, double endLat, double endLon) {
        double x = Math.cos(startLat * (Math.PI / 180)) *
                Math.cos(startLon * (Math.PI / 180)) *
                Math.cos(endLat * (Math.PI / 180)) *
                Math.cos(endLon * (Math.PI / 180));

        double y = Math.cos(startLat * (Math.PI / 180)) *
                Math.sin(startLon * (Math.PI / 180)) *
                Math.cos(endLat * (Math.PI / 180)) *
                Math.sin(endLon * (Math.PI / 180));

        double z = Math.sin(startLat * (Math.PI / 180)) *
                Math.sin(endLat * (Math.PI / 180));

        return Math.acos(x + y + z);
    }
}
