package uk.gov.dwp.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
        // Todo
        return Optional.empty();
    }

    private List<User> getListOfUsers(String endPoint) {
        log.info("Providing users from: {}", endPoint);

        Optional<User[]> users = this.makeRequest(endPoint);
        if (users.isEmpty()) {
            log.warn("Got 0 users back");
            return Collections.emptyList();
        }

        log.info("Got {} users back", users.get().length);
        return Arrays.stream(users.get()).collect(Collectors.toList());
    }

    private Optional<User[]> makeRequest(String endPoint) {
        ResponseEntity<User[]> response = restTemplate.getForEntity(endPoint, User[].class);
        HttpStatus responseStatus = response.getStatusCode();
        User[] responseUsers = response.getBody();

        if (responseStatus != HttpStatus.OK) {
            log.error("Error - HTTP response was not OK, got: {}", responseStatus);
            return Optional.empty();
        }

        if (responseUsers == null) {
            log.error("Error - HTTP response body is null");
            return Optional.empty();
        }

        return Optional.of(responseUsers);
    }
}
