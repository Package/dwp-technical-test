package uk.gov.dwp.users.domain;

import lombok.Getter;

public enum Location {
    BIRMINGHAM("Birmingham"),
    BLACKPOOL("Blackpool"),
    LEEDS("Leeds"),
    LONDON("London"),
    MANCHESTER("Manchester"),
    NEWCASTLE("Newcastle"),
    SHEFFIELD("Sheffield");

    @Getter
    final String name;

    Location(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
