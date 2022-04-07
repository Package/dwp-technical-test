package uk.gov.dwp.users.service;

import uk.gov.dwp.users.domain.Location;
import uk.gov.dwp.users.domain.User;

import java.util.List;

public interface UserProviderService {
    List<User> provideUsersInLocation(Location location);

    List<User> provideAllUsers();
}
