package uk.gov.dwp.users.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String INVALID_LOCATION_NAME = "not_a_location";

    private static final int USERS_COUNT_TOTAL = 1000;
    private static final int USERS_COUNT_IN_OR_NEARBY_LONDON = 9;

    @Test
    void getAllUsers_ReturnsAllUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users/"))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(USERS_COUNT_TOTAL))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].first_name").value("Maurise"))
                .andExpect(jsonPath("$.[0].last_name").value("Shieldon"))
                .andExpect(jsonPath("$.[0].email").value("mshieldon0@squidoo.com"))
                .andExpect(jsonPath("$.[0].ip_address").value("192.57.232.111"))
                .andExpect(jsonPath("$.[0].latitude").value(34.003135))
                .andExpect(jsonPath("$.[0].longitude").value(-117.7228641));
    }

    @Test
    void getUsersByLocation_ReturnsExpectedUsersInOrNearbyLondon() throws Exception {
        mockMvc.perform(get("/api/v1/users/location?name=london"))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(USERS_COUNT_IN_OR_NEARBY_LONDON))
                .andExpect(jsonPath("$.[0].id").value(135))
                .andExpect(jsonPath("$.[0].first_name").value("Mechelle"))
                .andExpect(jsonPath("$.[0].last_name").value("Boam"))
                .andExpect(jsonPath("$.[0].email").value("mboam3q@thetimes.co.uk"))
                .andExpect(jsonPath("$.[0].ip_address").value("113.71.242.187"))
                .andExpect(jsonPath("$.[0].latitude").value(-6.5115909))
                .andExpect(jsonPath("$.[0].longitude").value(105.652983));
    }

    @Test
    void getUsersByLocation_ReturnsBadRequest_GivenAnInvalidLocation() throws Exception {
        mockMvc.perform(get("/api/v1/users/location?name=" + INVALID_LOCATION_NAME))
                .andDo(log())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Location not supported: " + INVALID_LOCATION_NAME))
                .andExpect(jsonPath("$.statusCode").value("BAD_REQUEST"));
    }

    @Test
    void getUserById_ReturnsAUser_GivenAValidId() throws Exception {
        mockMvc.perform(get("/api/v1/users/1"))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.first_name").value("Maurise"))
                .andExpect(jsonPath("$.last_name").value("Shieldon"))
                .andExpect(jsonPath("$.email").value("mshieldon0@squidoo.com"))
                .andExpect(jsonPath("$.ip_address").value("192.57.232.111"))
                .andExpect(jsonPath("$.latitude").value(34.003135))
                .andExpect(jsonPath("$.longitude").value(-117.7228641))
                .andExpect(jsonPath("$.city").value("Kax"));
    }

    @Test
    void getUserById_ReturnsNotFound_GivenAnInvalidId() throws Exception {
        mockMvc.perform(get("/api/v1/users/9999999"))
                .andDo(log())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found with ID: 9999999"))
                .andExpect(jsonPath("$.statusCode").value("NOT_FOUND"));
    }
}
