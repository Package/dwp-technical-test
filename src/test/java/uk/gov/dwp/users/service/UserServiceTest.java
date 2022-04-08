package uk.gov.dwp.users.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.dwp.users.domain.Location;
import uk.gov.dwp.users.domain.User;
import uk.gov.dwp.users.exception.UserNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.dwp.users.data.MockUserData.FIRST_USER;
import static uk.gov.dwp.users.data.MockUserData.MOCKED_USERS;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService underTest;

    @Mock
    private UserProviderService userProviderService;

    @BeforeEach
    void setUp() {
        this.underTest = new UserService(userProviderService);
    }

    @Test
    void getUsersInLocation_ReturnsAnEmptyList_GivenNoUsers() {
        when(userProviderService.provideUsersInLocation(Location.LONDON)).thenReturn(Collections.emptyList());

        List<User> usersInLondon = underTest.getUsersInLocation(Location.LONDON);

        assertTrue(usersInLondon.isEmpty());
        verify(userProviderService).provideUsersInLocation(Location.LONDON);
    }

    @Test
    void getUsersInLocation_ReturnsListOfUsers_GivenUsersExist() {
        when(userProviderService.provideUsersInLocation(Location.LONDON)).thenReturn(MOCKED_USERS);

        List<User> usersInLondon = underTest.getUsersInLocation(Location.LONDON);

        assertSame(MOCKED_USERS, usersInLondon);
        verify(userProviderService).provideUsersInLocation(Location.LONDON);
    }

    @Test
    void getAllUsers_ReturnsListOfUsers_GivenUsersExist() {
        when(userProviderService.provideAllUsers()).thenReturn(MOCKED_USERS);

        List<User> allUsers = underTest.getAllUsers();

        assertSame(MOCKED_USERS, allUsers);
        verify(userProviderService).provideAllUsers();
    }

    @Test
    void getAllUsers_ReturnsAnEmptyList_GivenNoUsers() {
        when(userProviderService.provideAllUsers()).thenReturn(Collections.emptyList());

        List<User> allUsers = underTest.getAllUsers();

        assertTrue(allUsers.isEmpty());
        verify(userProviderService).provideAllUsers();
    }

    @Test
    void getUserById_ReturnsAUser_GivenValidId() {
        int validId = 1;
        when(userProviderService.provideUserById(validId)).thenReturn(Optional.of(FIRST_USER));

        User user = underTest.getUserById(validId);

        assertEquals(FIRST_USER.getId(), user.getId());
        assertSame(FIRST_USER, user);
        verify(userProviderService).provideUserById(validId);
    }

    @Test
    void getUserById_ThrowsNotFoundException_GivenAnInvalidId() {
        int invalidId = 999999;
        when(userProviderService.provideUserById(invalidId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> underTest.getUserById(invalidId));

        assertInstanceOf(UserNotFoundException.class, exception);
        assertEquals("User not found with ID: " + invalidId, exception.getMessage());
        verify(userProviderService).provideUserById(invalidId);
    }
}