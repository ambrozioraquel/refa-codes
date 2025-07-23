package survey_alerts.user.controller;

import org.springframework.web.bind.annotation.*;
import survey_alerts.user.dto.UserDTO;
import survey_alerts.user.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO user) {
        return userService.createUser(user);
    }

    @GetMapping("/by-ids")
    public List<UserDTO> getUsersByIds(@RequestParam List<Long> id) {
        return userService.findByIdInAndActiveTrue(id);
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.findAllUsers();
    }

}
