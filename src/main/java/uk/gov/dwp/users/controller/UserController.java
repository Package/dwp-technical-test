package uk.gov.dwp.users.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.dwp.users.domain.User;
import uk.gov.dwp.users.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUsersInLondon() {
        List<User> usersInLondon = userService.getUsersInLondon();

        return ResponseEntity.ok(usersInLondon);
    }
}
