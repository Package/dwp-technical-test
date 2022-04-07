package uk.gov.dwp.users.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
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

    @Test
    void getUsersInLondon_ReturnsAListOfUsers() {
        when(userService.getUsersInLondon()).thenReturn(MOCKED_USERS);

        ResponseEntity<List<User>> response = underTest.getUsersInLondon();
        List<User> usersInLondon = response.getBody();

        assertNotNull(usersInLondon);
        assertSame(MOCKED_USERS, usersInLondon);
        verify(userService).getUsersInLondon();
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