package survey_alerts.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import survey_alerts.user.entity.User;
import survey_alerts.user.exception.UserQueryException;
import survey_alerts.user.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findByIdInAndActiveTrue(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return Collections.emptyList();
        try {
            return userRepository.findByIdInAndActiveTrue(ids);

        } catch (Exception e) {
            throw new UserQueryException("Erro ao consultar usuários ativos.", e);
        }
    }

    public List<User> findAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new UserQueryException("Erro ao consultar todos os usuários.", e);
        }
    }
}
