package com.terratech.api.services;

import com.terratech.api.dto.user.UserRequest;
import com.terratech.api.dto.user.UserResponse;
import com.terratech.api.error.exceptions.ConflictException;
import com.terratech.api.error.exceptions.NotFoundException;
import com.terratech.api.model.Address;
import com.terratech.api.model.Residue;
import com.terratech.api.model.User;
import com.terratech.api.repositories.UserRepository;
import com.terratech.api.services.implementaions.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl userService;

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

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);

        userService = new UserServiceImpl(repository, mapper);
    }

    //  FIND BY ID TESTES
    @Test
    void shouldReturnUserById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(user));

        var dateNow = LocalDate.now();
        UserResponse getUser = userService.findById(1L);

        assertNotNull(getUser);
        assertEquals(user.getName(), getUser.name());
        assertEquals(user.getEmail(), getUser.email());
        assertEquals(user.getAddress(), getUser.address());
        assertEquals(user.getResidues(), getUser.residues());
        assertEquals(user.getResidues().size(), getUser.residues().size());

        var age = getUser.dateOfBirth().until(dateNow).getYears();

        assertEquals(22, age);
        assertTrue(17 < age);

    }

    @Test
    void shouldReturnUserByIdWithException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.findById(1L));
    }

    //    UPDATE TESTES
    @Test
    void shouldUpdateUser() {
        User update = User.builder()
                .name("John Doe")
                .email("email@email.com")
                .build();
        UserRequest request = new UserRequest(update);

        when(repository.findById(anyLong())).thenReturn(Optional.of(user));
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(User.class))).thenReturn(user);

        userService.update(1L, request);

        verify(repository, Mockito.times(1)).findById(anyLong());
        verify(repository, Mockito.times(1)).findByEmail(anyString());
        verify(repository, Mockito.times(1)).save(any(User.class));


        assertEquals("John Doe", user.getName());
        assertEquals("email@email.com", user.getEmail());
        assertNotNull(user.getPassword());
        assertNotNull(user.getDateOfBirth());
        assertNotNull(user.getAddress());
        assertNotNull(user.getResidues());
        assertFalse(user.getResidues().isEmpty());
    }

    @Test
    void shouldUpdateUserWithExceptionUserNotFound() {
        User update = User.builder()
                .name("John Doe")
                .email("email@email.com")
                .build();
        UserRequest request = new UserRequest(update);

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.userService.update(1L, request), "User not found");
    }

    @Test
    void shouldUpdateUserWithExceptionEmailAlreadyExists() {
        User update = User.builder()
                .name("John Doe")
                .email("email@email.com")
                .build();
        UserRequest request = new UserRequest(update);

        when(repository.findById(anyLong())).thenReturn(Optional.of(user));
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(ConflictException.class, () -> this.userService.update(1L, request), "Email already exists");
    }

    //    DELETE TESTES
    @Test
    void shouldDeleteUser() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(user));

        this.userService.delete(1L);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(User.class));
    }

    @Test
    void shouldDeleteUserWithException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.userService.delete(1L), "User not found");
    }
}
