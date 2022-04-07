package uk.gov.dwp.users.domain.http;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponseFactory {

    public static ErrorResponse badRequest(String message) {
        return new ErrorResponse(message, HttpStatus.BAD_REQUEST);
    }
}
