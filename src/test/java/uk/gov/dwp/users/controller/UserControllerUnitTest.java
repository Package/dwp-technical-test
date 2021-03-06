package uk.gov.dwp.users.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import uk.gov.dwp.users.domain.Location;
import uk.gov.dwp.users.domain.User;
import uk.gov.dwp.users.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.dwp.users.data.MockUserData.FIRST_USER;
import static uk.gov.dwp.users.data.MockUserData.MOCKED_USERS;

@ExtendWith(MockitoExtension.class)
class UserControllerUnitTest {

    private UserController underTest;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        this.underTest = new UserController(userService);
    }

    @ParameterizedTest
    @EnumSource(value = Location.class)
    void getUsersInOrNearbyLocation_ReturnsAListOfUsers_GivenAValidLocation(Location location) {
        when(userService.getUsersInOrNearbyLocation(location)).thenReturn(MOCKED_USERS);

        ResponseEntity<List<User>> response = underTest.getUsersByLocation(location);
        List<User> usersInLocation = response.getBody();

        assertNotNull(usersInLocation);
        assertEquals(MOCKED_USERS.size(), usersInLocation.size());
        verify(userService).getUsersInOrNearbyLocation(location);
    }

    @Test
    void getAllUsers_ReturnsAListOfUsers() {
        when(userService.getAllUsers()).thenReturn(MOCKED_USERS);

        ResponseEntity<List<User>> response = underTest.getAllUsers();
        List<User> allUsers = response.getBody();

        assertNotNull(allUsers);
        assertSame(MOCKED_USERS, allUsers);
        verify(userService).getAllUsers();
    }

    @Test
    void getUserById_ReturnsAUser_GivenAValidId() {
        int validId = 100;
        when(userService.getUserById(validId)).thenReturn(FIRST_USER);

        ResponseEntity<User> response = underTest.getUserById(validId);
        User user = response.getBody();

        assertNotNull(user);
        assertSame(FIRST_USER, user);
        verify(userService).getUserById(validId);
    }
}