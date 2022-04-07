package uk.gov.dwp.users.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.dwp.users.domain.Location;
import uk.gov.dwp.users.domain.User;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
}