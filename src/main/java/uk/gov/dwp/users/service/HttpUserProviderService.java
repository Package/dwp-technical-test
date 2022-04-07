package uk.gov.dwp.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.gov.dwp.users.domain.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HttpUserProviderService implements UserProviderService {

    private final RestTemplate restTemplate;

    @Value("${api.base.url}")
    private String baseUrl;

    @Override
    public List<User> provideUsersInLondon() {
        String endPoint = String.format("%s/city/London/users", baseUrl);
        log.info("Providing users from: {}", endPoint);

        ResponseEntity<User[]> response = restTemplate.getForEntity(endPoint, User[].class);
        HttpStatus responseStatus = response.getStatusCode();
        User[] responseUsers = response.getBody();

        if (responseStatus != HttpStatus.OK) {
            log.error("Error - HTTP response was not OK, got: {}", responseStatus);
            return Collections.emptyList();
        }

        if (responseUsers == null) {
            log.error("Error - HTTP response body is null");
            return Collections.emptyList();
        }

        log.info("Got {} users back", responseUsers.length);
        return Arrays.stream(response.getBody()).collect(Collectors.toList());
    }
}
