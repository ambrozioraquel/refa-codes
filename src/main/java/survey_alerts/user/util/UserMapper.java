package survey_alerts.user.util;

import survey_alerts.user.entity.User;
import survey_alerts.user.dto.UserDTO;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        if (user == null) return null;
        return new UserDTO(
                user.getId(),
                user.getLogin(),
                user.getName(),
                user.getEmail(),
                user.getCreated(),
                user.getLastUpdate(),
                user.getActive()
        );
    }

    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) return null;
        return new User(
                userDTO.getId(),
                userDTO.getLogin(),
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.getCreated(),
                userDTO.getLastUpdate(),
                userDTO.getActive()
        );
    }
}
