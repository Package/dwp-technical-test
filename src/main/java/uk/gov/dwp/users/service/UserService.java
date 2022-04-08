package uk.gov.dwp.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.gov.dwp.users.domain.Location;
import uk.gov.dwp.users.domain.User;
import uk.gov.dwp.users.exception.UserNotFoundException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserProviderService userProviderService;

    public List<User> getUsersInLocation(Location location) {
        return userProviderService.provideUsersInLocation(location);
    }

    public List<User> getAllUsers() {
        return userProviderService.provideAllUsers();
    }

    public User getUserById(int userId) {
        return userProviderService.provideUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }
}
