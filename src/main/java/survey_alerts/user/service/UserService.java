package survey_alerts.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import survey_alerts.user.dto.UserDTO;
import survey_alerts.user.entity.User;
import survey_alerts.user.exception.UserQueryException;
import survey_alerts.user.repository.UserRepository;
import survey_alerts.user.util.UserMapper;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        return UserMapper.toDTO(userRepository.save(user));
    }

    public List<UserDTO> findByIdInAndActiveTrue(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return Collections.emptyList();
        try {
            return userRepository.findByIdInAndActiveTrue(ids)
                    .stream()
                    .map(UserMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            throw new UserQueryException("Erro ao consultar usuários ativos.", e);
        }
    }

    public List<UserDTO> findAllUsers() {
        try {
            return userRepository.findAll()
                    .stream()
                    .map(UserMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            throw new UserQueryException("Erro ao consultar todos os usuários.", e);
        }
    }
}
