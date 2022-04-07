package uk.gov.dwp.users.data;

import uk.gov.dwp.users.domain.User;

import java.util.List;

public class MockUserData {
    public static final User FIRST_USER = new User(1, "John", "Example",
            "john@example.com", "113.71.242.187",
            -6.5115909, 105.652983);

    public static final User SECOND_USER = new User(1, "Jane", "Example",
            "jane@example.com", "28.146.197.176",
            27.69417, 109.73583);

    public static final User THIRD_USER = new User(1, "Jimmy", "Example",
            "jimmy@example.com", "187.79.141.124",
            -8.1844859, 113.6680747);

    public static final List<User> MOCKED_USERS = List.of(FIRST_USER, SECOND_USER, THIRD_USER);
}
