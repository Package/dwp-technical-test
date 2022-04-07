package uk.gov.dwp.users.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    void getUsersInLondon_ReturnsAnEmptyList_GivenNoUsers() {
        when(userProviderService.provideUsersInLondon()).thenReturn(Collections.emptyList());

        List<User> usersInLondon = underTest.getUsersInLondon();

        assertTrue(usersInLondon.isEmpty());
        verify(userProviderService).provideUsersInLondon();
    }

    @Test
    void getUsersInLondon_ReturnsListOfUsers_GivenUsersExist() {
        when(userProviderService.provideUsersInLondon()).thenReturn(MOCKED_USERS);

        List<User> usersInLondon = underTest.getUsersInLondon();

        assertSame(MOCKED_USERS, usersInLondon);
        verify(userProviderService).provideUsersInLondon();
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