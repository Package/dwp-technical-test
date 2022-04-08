package uk.gov.dwp.users.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uk.gov.dwp.users.domain.http.ErrorResponse;
import uk.gov.dwp.users.domain.http.ErrorResponseFactory;
import uk.gov.dwp.users.exception.LocationBadRequestException;
import uk.gov.dwp.users.exception.UserNotFoundException;

@RestControllerAdvice
public class ControllerExceptionConfig {

    @ResponseBody
    @ExceptionHandler(LocationBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse locationBadRequestException(LocationBadRequestException exception) {
        return ErrorResponseFactory.badRequest(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse missingServletRequestParameterException(MissingServletRequestParameterException exception) {
        return ErrorResponseFactory.badRequest(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse userNotFoundException(UserNotFoundException exception) {
        return ErrorResponseFactory.notFound(exception.getMessage());
    }
}
