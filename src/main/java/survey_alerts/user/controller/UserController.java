package survey_alerts.user.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import survey_alerts.user.dto.UserDTO;
import survey_alerts.user.entity.User;
import survey_alerts.user.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;


    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        User createdUser = userService.createUser(user);
        UserDTO createdUserDTO = modelMapper.map(createdUser, UserDTO.class);
        return ResponseEntity.ok(createdUserDTO);
    }

    @GetMapping("/by-ids")
    public ResponseEntity<List<UserDTO>> getUsersByIds(@RequestParam List<Long> id) {
        List<User> users = userService.findByIdInAndActiveTrue(id);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<UserDTO> userDTOs = users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<UserDTO> userDTOs = users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
        return ResponseEntity.ok(userDTOs);
    }

}
