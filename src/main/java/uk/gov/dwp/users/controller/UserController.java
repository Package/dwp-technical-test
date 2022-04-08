package uk.gov.dwp.users.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.dwp.users.domain.Location;
import uk.gov.dwp.users.domain.User;
import uk.gov.dwp.users.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();

        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/location")
    public ResponseEntity<List<User>> getUsersByLocation(@RequestParam(name = "name") Location location) {
        List<User> usersInLondon = userService.getUsersInLocation(location);

        return ResponseEntity.ok(usersInLondon);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
        User user = userService.getUserById(userId);

        return ResponseEntity.ok(user);
    }
}
