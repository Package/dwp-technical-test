package uk.gov.dwp.users.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.dwp.users.domain.Location;
import uk.gov.dwp.users.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.dwp.users.data.MockUserData.MOCKED_USERS;

@WebMvcTest
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getAllUsers_ReturnsAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(MOCKED_USERS);

        mockMvc.perform(get("/api/v1/users"))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(MOCKED_USERS.size()));

        verify(userService).getAllUsers();
    }

    @ParameterizedTest
    @EnumSource(Location.class)
    void getUsersByLocation_ReturnsUsers_GivenAValidLocation(Location location) throws Exception {
        when(userService.getUsersInLocation(location)).thenReturn(MOCKED_USERS);

        mockMvc.perform(get("/api/v1/users/location?name=" + location.getName()))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(MOCKED_USERS.size()));

        verify(userService).getUsersInLocation(location);
    }

    @ParameterizedTest
    @ValueSource(strings = {"not_a_location", "RANDOM PLACE"})
    void getUsersByLocation_ThrowsBadRequestException_GivenAnInvalidLocation(String invalidLocation) throws Exception {
        mockMvc.perform(get("/api/v1/users/location?name=" + invalidLocation))
                .andDo(log())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Location not supported: " + invalidLocation))
                .andExpect(jsonPath("$.statusCode").value("BAD_REQUEST"));

        verify(userService, never()).getUsersInLocation(any(Location.class));
    }

    @Test
    void getUsersByLocation_ThrowsBadRequestException_GivenAMissingLocationParam() throws Exception {
        mockMvc.perform(get("/api/v1/users/location"))
                .andDo(log())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value("BAD_REQUEST"));

        verify(userService, never()).getUsersInLocation(any(Location.class));
    }
}