package uk.gov.dwp.users.service;

import uk.gov.dwp.users.domain.Location;
import uk.gov.dwp.users.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserProviderService {
    List<User> provideUsersInLocation(Location location);

    List<User> provideAllUsers();

    Optional<User> provideUserById(int userId);
}
