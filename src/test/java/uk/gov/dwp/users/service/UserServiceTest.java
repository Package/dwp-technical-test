package uk.gov.dwp.users.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.dwp.users.domain.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new UserService();
    }

    @Test
    void getUsersInLondon_ReturnsAnEmptyList_GivenNoUsers() {
        List<User> usersInLondon = underTest.getUsersInLondon();

        assertTrue(usersInLondon.isEmpty());
    }
}