package uk.gov.dwp.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.dwp.users.domain.Location;
import uk.gov.dwp.users.domain.User;
import uk.gov.dwp.users.domain.http.ErrorResponse;
import uk.gov.dwp.users.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Tag(name = "User Controller", description = "Returns Information on Users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Returns all users.")
    @ApiResponse(
            responseCode = "200",
            description = "Returns all users successfully.",
            content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation
                    = User.class)))}
    )
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();

        return ResponseEntity.ok(allUsers);
    }

    @Operation(summary = "Returns all users in the provided location or located nearby (within 50 miles).")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns the users in or nearby this location successfully.",
                    content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema =
                            @Schema(implementation = User.class)))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "An invalid location was provided.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation =
                                    ErrorResponse.class))
                    }
            )
    })
    @GetMapping("/location")
    public ResponseEntity<List<User>> getUsersByLocation(
            @RequestParam(name = "name")
            @Parameter(description = "Only London has data.") Location location
    ) {
        List<User> usersInOrNearby = userService.getUsersInOrNearbyLocation(location);

        return ResponseEntity.ok(usersInOrNearby);
    }

    @Operation(summary = "Returns a single user by ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns the user successfully.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "A user couldn't be found with the provided ID.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation =
                                    ErrorResponse.class))
                    }
            )
    })
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
        User user = userService.getUserById(userId);

        return ResponseEntity.ok(user);
    }
}
