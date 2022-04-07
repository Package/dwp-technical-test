package uk.gov.dwp.users.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.dwp.users.domain.Location;
import uk.gov.dwp.users.domain.User;
import uk.gov.dwp.users.service.UserService;

import javax.validation.Valid;
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
    public ResponseEntity<List<User>> getUsersByLocation(@Valid @RequestParam(name = "name") Location location) {
        List<User> usersInLondon = userService.getUsersInLocation(location);

        return ResponseEntity.ok(usersInLondon);
    }
}
