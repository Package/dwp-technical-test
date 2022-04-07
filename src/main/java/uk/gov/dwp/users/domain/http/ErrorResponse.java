package uk.gov.dwp.users.domain.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private HttpStatus statusCode;
}
