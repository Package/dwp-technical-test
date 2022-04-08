package uk.gov.dwp.users.domain;

import lombok.Getter;

public enum Location {
    BIRMINGHAM("Birmingham", new Coordinate(52.4862, -1.8904)),
    BLACKPOOL("Blackpool", new Coordinate(53.8167, -3.0370)),
    LEEDS("Leeds", new Coordinate(53.8008, -1.5491)),
    LONDON("London", new Coordinate(51.5072, -0.1276)),
    MANCHESTER("Manchester", new Coordinate(53.4808, -2.2426)),
    NEWCASTLE("Newcastle", new Coordinate(54.9783, -1.6178)),
    SHEFFIELD("Sheffield", new Coordinate(53.3811, -1.4701));

    @Getter
    final String name;

    @Getter
    final Coordinate coordinates;

    Location(final String name, final Coordinate coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
