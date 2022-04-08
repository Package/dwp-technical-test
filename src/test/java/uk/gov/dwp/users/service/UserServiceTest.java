package uk.gov.dwp.users.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.dwp.users.domain.Coordinate;
import uk.gov.dwp.users.domain.Location;
import uk.gov.dwp.users.domain.User;
import uk.gov.dwp.users.exception.UserNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static uk.gov.dwp.users.data.MockUserData.FIRST_USER;
import static uk.gov.dwp.users.data.MockUserData.MOCKED_USERS;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService underTest;

    @Mock
    private UserProviderService userProviderService;

    @Mock
    private DistanceService distanceService;

    @BeforeEach
    void setUp() {
        this.underTest = new UserService(userProviderService, distanceService);
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

    @ParameterizedTest
    @ValueSource(doubles = {50.1, 51.0, 100.0, 1000.0})
    void getUsersNearbyLocation_ReturnsNoUsers_WhenAllUsersAreNotNearby(double distanceToReturn) {
        when(userProviderService.provideAllUsers()).thenReturn(MOCKED_USERS);
        when(distanceService.distanceBetweenCoordinatesInMiles(any(Coordinate.class), any(Coordinate.class)))
                .thenReturn(distanceToReturn);

        List<User> nearbyUsers = underTest.getUsersNearbyLocation(Location.NEWCASTLE);

        assertEquals(0, nearbyUsers.size());
        verify(userProviderService).provideAllUsers();
        verify(distanceService, times(MOCKED_USERS.size()))
                .distanceBetweenCoordinatesInMiles(any(Coordinate.class), any(Coordinate.class));
    }

    @ParameterizedTest
    @ValueSource(doubles = {25.0, 0.0, 50.0, 49.99})
    void getUsersNearbyLocation_ReturnsAllUsers_WhenAllUsersAreNearby(double distanceToReturn) {
        when(userProviderService.provideAllUsers()).thenReturn(MOCKED_USERS);
        when(distanceService.distanceBetweenCoordinatesInMiles(any(Coordinate.class), any(Coordinate.class)))
                .thenReturn(distanceToReturn);

        List<User> nearbyUsers = underTest.getUsersNearbyLocation(Location.LONDON);

        assertEquals(MOCKED_USERS.size(), nearbyUsers.size());
        verify(userProviderService).provideAllUsers();
        verify(distanceService, times(MOCKED_USERS.size()))
                .distanceBetweenCoordinatesInMiles(any(Coordinate.class), any(Coordinate.class));
    }

    @ParameterizedTest
    @EnumSource(Location.class)
    void getUsersInOrNearbyLocation_ReturnsAllUsers_WhenLocationIsValid(Location location) {
        when(userProviderService.provideAllUsers()).thenReturn(MOCKED_USERS);
        when(userProviderService.provideUsersInLocation(location)).thenReturn(MOCKED_USERS);

        List<User> usersInOrNearbyLocation = underTest.getUsersInOrNearbyLocation(location);

        assertEquals(MOCKED_USERS.size(), usersInOrNearbyLocation.size());
        verify(userProviderService).provideAllUsers();
        verify(userProviderService).provideUsersInLocation(location);
    }

    @ParameterizedTest
    @MethodSource("provideUsersToCombine")
    void combineUsers_ReturnsUniqueListOfUsers_GivenMultipleListsOfUsers(List<User> firstList, List<User> secondList,
                                                                         int expectedCombinedSize) {
        List<User> combinedList = underTest.combineUsers(firstList, secondList);

        assertEquals(expectedCombinedSize, combinedList.size());
    }

    private static Stream<Arguments> provideUsersToCombine() {
        return Stream.of(
                Arguments.of(MOCKED_USERS, MOCKED_USERS, MOCKED_USERS.size()),
                Arguments.of(List.of(new User()), Collections.emptyList(), 1),
                Arguments.of(List.of(MOCKED_USERS.get(1)), List.of(MOCKED_USERS.get(2)), 2),
                Arguments.of(Collections.emptyList(), Collections.emptyList(), 0)
        );
    }
}