package uk.gov.dwp.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.dwp.users.controller.UserController;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DwpTechnicalTestApplicationTests {

    @Autowired
    private UserController underTest;

    @Test
    void springContextLoadsAndControllerGetsAutowired() {
        assertNotNull(underTest);
    }
}
