package com.terratech.api.services;

import com.terratech.api.dto.Login;
import com.terratech.api.model.Collector;
import com.terratech.api.model.Residue;
import com.terratech.api.model.User;
import com.terratech.api.repositories.CollectorRepository;
import com.terratech.api.repositories.UserRepository;
import com.terratech.api.services.implementaions.AuthServiceImp;
import com.terratech.api.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static com.terratech.api.model.enums.Role.COLLECTOR;
import static com.terratech.api.model.enums.Role.USER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CollectorRepository collectorRepository;

    @InjectMocks
    private AuthServiceImp authService;

    @BeforeEach
    void setup() {
        authService = new AuthServiceImp(userRepository, collectorRepository);
    }

    @Test
    void shouldRegisterUser() {
        User user = User.builder()
                .name("Test")
                .email("test@gmail.com")
                .password("123456")
                .dateOfBirth(LocalDate.of(1999, 1, 1))
                .residues(List.of(new Residue()))
                .role(USER)
                .build();

        when(userRepository.save(any(User.class))).thenReturn(user);

        String response = authService.register(user);
        String username = JwtUtil.extractUsername(response);
        verify(userRepository, times(1)).save(any(User.class));

        assertNotNull(response, "Usuario retornado não pode ser nulo");
        assertFalse(response.isEmpty(), "Token do usuario não pode ser vazio");
    }

    @Test
    void shouldRegisterCollector() {
        Collector collector = Collector.builder()
                .name("Test")
                .email("test@gmail.com")
                .password("123456")
                .dateOfBirth(LocalDate.of(1999, 1, 1))
                .role(COLLECTOR)
                .build();

        when(collectorRepository.save(any(Collector.class))).thenReturn(collector);

        String response = authService.register(collector);



        assertNotNull(response, "Coletor retornado não pode ser nulo");
        assertFalse(response.isEmpty(), "Token do coletor não pode ser vazio");
    }

    @Test
    void shouldLoginUser() {
        Login login = new Login("teste@gmail", "123456");

        String response = authService.login(login);

        assertNotNull(response, "Token do usuario não pode ser nulo");
        assertFalse(response.isEmpty(), "Token do usuario não pode ser vazio");
    }
}