package core.user;


import core.user.entity.User;
import core.user.exception.UserQueryException;
import core.user.repository.UserRepository;
import core.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void deveCriarUsuario() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertEquals(user, result);
        verify(userRepository).save(user);
    }

    @Test
    void deveRetornarUsuariosAtivosPorId() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findByIdInAndActiveTrue(ids)).thenReturn(users);

        List<User> result = userService.findByIdInAndActiveTrue(ids);

        assertEquals(2, result.size());
        verify(userRepository).findByIdInAndActiveTrue(ids);
    }

    @Test
    void deveRetornarListaVaziaSeIdsNulosOuVazios() {
        assertTrue(userService.findByIdInAndActiveTrue(null).isEmpty());
        assertTrue(userService.findByIdInAndActiveTrue(Collections.emptyList()).isEmpty());
        verify(userRepository, never()).findByIdInAndActiveTrue(any());
    }

    @Test
    void deveLancarExcecaoAoBuscarUsuariosAtivos() {
        List<Long> ids = List.of(1L);
        when(userRepository.findByIdInAndActiveTrue(ids)).thenThrow(new RuntimeException("erro"));

        assertThrows(UserQueryException.class, () -> userService.findByIdInAndActiveTrue(ids));
    }

    @Test
    void deveRetornarTodosUsuarios() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAllUsers();

        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void deveLancarExcecaoAoBuscarTodosUsuarios() {
        when(userRepository.findAll()).thenThrow(new RuntimeException("erro"));

        assertThrows(UserQueryException.class, () -> userService.findAllUsers());
    }
}