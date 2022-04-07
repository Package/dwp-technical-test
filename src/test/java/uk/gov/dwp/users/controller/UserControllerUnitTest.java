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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
    void getUsersByLocation_ReturnsAListOfUsers_GivenAValidLocation(Location location) {
        when(userService.getUsersInLocation(location)).thenReturn(MOCKED_USERS);

        ResponseEntity<List<User>> response = underTest.getUsersByLocation(location);
        List<User> usersInLondon = response.getBody();

        assertNotNull(usersInLondon);
        assertSame(MOCKED_USERS, usersInLondon);
        verify(userService).getUsersInLocation(location);
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
}