package uk.gov.dwp.users.exception;

public class LocationBadRequestException extends RuntimeException {
    public LocationBadRequestException(String message) {
        super(message);
    }
}
