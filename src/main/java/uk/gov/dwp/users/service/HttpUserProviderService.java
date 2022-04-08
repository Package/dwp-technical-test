package uk.gov.dwp.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uk.gov.dwp.users.domain.Location;
import uk.gov.dwp.users.domain.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HttpUserProviderService implements UserProviderService {

    private final RestTemplate restTemplate;

    @Value("${api.base.url}")
    private String baseUrl;

    @Override
    public List<User> provideUsersInLocation(Location location) {
        String endPoint = String.format("%s/city/%s/users", baseUrl, location.getName());

        return this.getListOfUsers(endPoint);
    }

    @Override
    public List<User> provideAllUsers() {
        String endPoint = String.format("%s/users", baseUrl);

        return this.getListOfUsers(endPoint);
    }

    @Override
    public Optional<User> provideUserById(int userId) {
        log.info("Finding a user with ID: {}", userId);

        String endPoint = String.format("%s/user/%d", baseUrl, userId);

        Optional<User> user = this.makeRequest(endPoint, User.class);
        if (user.isEmpty()) {
            log.warn("No user found with ID: {}", userId);
            return Optional.empty();
        }

        return user;
    }

    private List<User> getListOfUsers(String endPoint) {
        log.info("Providing users from: {}", endPoint);

        Optional<User[]> users = this.makeRequest(endPoint, User[].class);
        if (users.isEmpty()) {
            log.warn("Got 0 users back");
            return Collections.emptyList();
        }

        log.info("Got {} users back", users.get().length);
        return Arrays.stream(users.get()).collect(Collectors.toList());
    }

    private <T> Optional<T> makeRequest(String endPoint, Class<T> className) {
        try {
            ResponseEntity<T> response = restTemplate.getForEntity(endPoint, className);
            HttpStatus responseStatus = response.getStatusCode();
            T responseBody = response.getBody();

            if (responseStatus != HttpStatus.OK) {
                log.error("Error - HTTP response was not OK, got: {}", responseStatus);
                return Optional.empty();
            }

            if (responseBody == null) {
                log.error("Error - HTTP response body is null");
                return Optional.empty();
            }

            return Optional.of(responseBody);
        } catch (HttpClientErrorException exception) {
            log.error("Error - exception caught making HTTP call: {}", exception.getMessage());
        }

        return Optional.empty();
    }
}
