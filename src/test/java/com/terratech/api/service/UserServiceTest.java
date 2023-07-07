package com.terratech.api.service;

import com.terratech.api.dto.UserRequest;
import com.terratech.api.model.Address;
import com.terratech.api.model.Residue;
import com.terratech.api.model.User;
import com.terratech.api.repositories.UserRepository;
import com.terratech.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository repository;

    @Autowired
    private UserService userService;

    private User user;

    @BeforeEach
    void setup() {
        user = User.builder()
                .id(1L)
                .name("Teste")
                .email("teste@gmail.com")
                .password("teste123@")
                .dateOfBirth(LocalDate.of(2000, 10, 10))
                .address(new Address("12345678", "92"))
                .residues(List.of(new Residue()))
                .build();
    }
//  FIND BY ID TESTES
    @Test
    void shouldReturnUserById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(user));

        var dateNow = LocalDate.now();
        User getUser = userService.findById(1L);

        assertNotNull(getUser);
        assertEquals(1L, getUser.getId());

        var age = getUser.getDateOfBirth().until(dateNow).getYears();

        assertEquals(22, age);
        assertTrue(17 < age);

    }

    @Test
    void shouldReturnUserByIdWithException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.findById(1L));
    }

//    CREATE TESTES
    @Test
    void shouldCreateUser() {
        UserRequest request = new UserRequest(user);

        when(repository.save(any(User.class))).thenReturn(user);
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        var createdUser = userService.create(request);

        verify(repository, Mockito.times(1)).save(any(User.class));
        verify(repository, Mockito.times(1)).findByEmail(anyString());

        assertNotNull(createdUser);
        assertEquals(user, createdUser);
        assertEquals(user.getId(), createdUser.getId());
        assertEquals(user.getName(), createdUser.getName());
        assertEquals(user.getEmail(), createdUser.getEmail());
        assertEquals(user.getPassword(), createdUser.getPassword());
        assertEquals(user.getDateOfBirth(), createdUser.getDateOfBirth());
        assertEquals(user.getAddress(), createdUser.getAddress());
        assertEquals(user.getResidues(), createdUser.getResidues());
        assertFalse( createdUser.getResidues().isEmpty());
    }

    @Test
    void shouldCreateUserWithException() {
        UserRequest request = new UserRequest(user);

        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));
        assertThrows(RuntimeException.class, () -> userService.create(request));
    }

}
