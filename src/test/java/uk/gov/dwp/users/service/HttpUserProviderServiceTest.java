package uk.gov.dwp.users.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import uk.gov.dwp.users.domain.Location;
import uk.gov.dwp.users.domain.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.dwp.users.data.MockUserData.FIRST_USER;
import static uk.gov.dwp.users.data.MockUserData.MOCKED_USERS;

@ExtendWith(MockitoExtension.class)
class HttpUserProviderServiceTest {

    private static final String TEST_BASE_URL = "https://example.com";
    private static final User[] MOCKED_USERS_ARRAY = MOCKED_USERS.toArray(new User[0]);

    private UserProviderService underTest;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        this.underTest = new HttpUserProviderService(restTemplate);

        ReflectionTestUtils.setField(underTest, "baseUrl", TEST_BASE_URL);
    }

    @Test
    void provideUsersByLocation_ReturnsListOfUsers_GivenSuccessfulResponse() {
        String endPoint = String.format("%s/city/London/users", TEST_BASE_URL);
        when(restTemplate.getForEntity(endPoint, User[].class)).thenReturn(new ResponseEntity<>(MOCKED_USERS_ARRAY,
                HttpStatus.OK));

        List<User> usersInLondon = underTest.provideUsersInLocation(Location.LONDON);

        assertEquals(MOCKED_USERS_ARRAY.length, usersInLondon.size());
        verify(restTemplate).getForEntity(endPoint, User[].class);
    }

    @Test
    void provideUsersByLocation_ReturnsEmptyList_GivenNullResponseBody() {
        String endPoint = String.format("%s/city/London/users", TEST_BASE_URL);
        when(restTemplate.getForEntity(endPoint, User[].class)).thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        List<User> usersInLondon = underTest.provideUsersInLocation(Location.LONDON);

        assertTrue(usersInLondon.isEmpty());
        verify(restTemplate).getForEntity(endPoint, User[].class);
    }

    @Test
    void provideUsersByLocation_ReturnsEmptyList_GivenBadResponseStatus() {
        String endPoint = String.format("%s/city/London/users", TEST_BASE_URL);
        when(restTemplate.getForEntity(endPoint, User[].class)).thenReturn(new ResponseEntity<>(null,
                HttpStatus.NOT_FOUND));

        List<User> usersInLondon = underTest.provideUsersInLocation(Location.LONDON);

        assertTrue(usersInLondon.isEmpty());
        verify(restTemplate).getForEntity(endPoint, User[].class);
    }

    @Test
    void provideAllUsers_ReturnsListOfUsers_GivenSuccessfulResponse() {
        String endPoint = String.format("%s/users", TEST_BASE_URL);
        when(restTemplate.getForEntity(endPoint, User[].class)).thenReturn(new ResponseEntity<>(MOCKED_USERS_ARRAY,
                HttpStatus.OK));

        List<User> allUsers = underTest.provideAllUsers();

        assertEquals(MOCKED_USERS_ARRAY.length, allUsers.size());
        verify(restTemplate).getForEntity(endPoint, User[].class);
    }

    @Test
    void provideAllUsers_ReturnsEmptyList_GivenNullResponseBody() {
        String endPoint = String.format("%s/users", TEST_BASE_URL);
        when(restTemplate.getForEntity(endPoint, User[].class)).thenReturn(new ResponseEntity<>(null,
                HttpStatus.OK));

        List<User> allUsers = underTest.provideAllUsers();

        assertTrue(allUsers.isEmpty());
        verify(restTemplate).getForEntity(endPoint, User[].class);
    }

    @Test
    void provideAllUsers_ReturnsEmptyListOfUsers_GivenBadResponseStatus() {
        String endPoint = String.format("%s/users", TEST_BASE_URL);
        when(restTemplate.getForEntity(endPoint, User[].class)).thenReturn(new ResponseEntity<>(MOCKED_USERS_ARRAY,
                HttpStatus.NOT_FOUND));

        List<User> allUsers = underTest.provideAllUsers();

        assertTrue(allUsers.isEmpty());
        verify(restTemplate).getForEntity(endPoint, User[].class);
    }

    @Test
    void provideUserById_ReturnsUser_GivenValidId() {
        int validId = 1;
        String endPoint = String.format("%s/user/%d", TEST_BASE_URL, validId);
        when(restTemplate.getForEntity(endPoint, User.class)).thenReturn(new ResponseEntity<>(FIRST_USER,
                HttpStatus.OK));

        Optional<User> userById = underTest.provideUserById(validId);

        assertTrue(userById.isPresent());
        verify(restTemplate).getForEntity(endPoint, User.class);
    }

    @Test
    void provideUserById_ReturnsEmptyOptional_GivenNullResponseBody() {
        int invalidId = 999999;
        String endPoint = String.format("%s/user/%d", TEST_BASE_URL, invalidId);
        when(restTemplate.getForEntity(endPoint, User.class)).thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        Optional<User> userById = underTest.provideUserById(invalidId);

        assertTrue(userById.isEmpty());
        verify(restTemplate).getForEntity(endPoint, User.class);
    }

    @Test
    void provideUserById_ReturnsEmptyOptional_GivenBadResponseCode() {
        int invalidId = 999999;
        String endPoint = String.format("%s/user/%d", TEST_BASE_URL, invalidId);
        when(restTemplate.getForEntity(endPoint, User.class)).thenReturn(new ResponseEntity<>(FIRST_USER,
                HttpStatus.NOT_FOUND));

        Optional<User> userById = underTest.provideUserById(invalidId);

        assertTrue(userById.isEmpty());
        verify(restTemplate).getForEntity(endPoint, User.class);
    }
}