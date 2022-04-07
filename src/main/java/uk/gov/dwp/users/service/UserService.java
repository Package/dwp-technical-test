package uk.gov.dwp.users.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.gov.dwp.users.domain.User;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class UserService {

    public List<User> getUsersInLondon() {
        return Collections.emptyList();
    }
}
