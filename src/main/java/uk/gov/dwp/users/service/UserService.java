package uk.gov.dwp.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.gov.dwp.users.domain.User;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserProviderService userProviderService;

    public List<User> getUsersInLondon() {
        return userProviderService.provideUsersInLondon();
    }

    public List<User> getAllUsers() {
        return userProviderService.provideAllUsers();
    }

}
