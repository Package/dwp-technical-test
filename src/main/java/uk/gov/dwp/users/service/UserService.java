package uk.gov.dwp.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.gov.dwp.users.domain.Location;
import uk.gov.dwp.users.domain.User;
import uk.gov.dwp.users.exception.UserNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserProviderService userProviderService;
    private final DistanceService distanceService;

    public List<User> getUsersInOrNearbyLocation(Location location) {
        List<User> inLocation = getUsersInLocation(location);
        List<User> nearbyLocation = getUsersNearbyLocation(location);

        return combineUsers(inLocation, nearbyLocation);
    }

    public List<User> getUsersInLocation(Location location) {
        log.info("Finding users in: {}", location.getName());

        return userProviderService.provideUsersInLocation(location);
    }

    public List<User> getAllUsers() {
        log.info("Finding all users");

        return userProviderService.provideAllUsers();
    }

    public User getUserById(int userId) {
        log.info("Finding user with ID: {}", userId);

        return userProviderService.provideUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    public List<User> getUsersNearbyLocation(Location location) {
        log.info("Finding users nearby {}", location.getName());

        List<User> allUsers = getAllUsers();

        Predicate<User> nearbyFilter = user -> distanceService.distanceBetweenCoordinatesInMiles(
                location.getCoordinates(), user.getCoordinates()) <= 50.0;

        List<User> nearbyUsers = allUsers.stream().filter(nearbyFilter).collect(Collectors.toList());

        log.info("Found {} users nearby {}", nearbyUsers.size(), location.getName());

        return nearbyUsers;
    }

    public List<User> combineUsers(List<User> first, List<User> second) {
        return Stream.of(first, second)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }
}
