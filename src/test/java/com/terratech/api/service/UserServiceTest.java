package com.terratech.api.service;

import com.terratech.api.dto.user.UserRequest;
import com.terratech.api.exception.ConflictException;
import com.terratech.api.exception.NotFoundException;
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
        assertThrows(NotFoundException.class, () -> userService.findById(1L));
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
        assertFalse(createdUser.getResidues().isEmpty());
    }

    @Test
    void shouldCreateUserWithException() {
        UserRequest request = new UserRequest(user);

        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));
        assertThrows(ConflictException.class, () -> userService.create(request));
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

        var updatedUser = userService.update(1L, request);

        verify(repository, Mockito.times(1)).findById(anyLong());
        verify(repository, Mockito.times(1)).findByEmail(anyString());
        verify(repository, Mockito.times(1)).save(any(User.class));

        assertNotNull(updatedUser);
        assertEquals(user, updatedUser);
        assertEquals(1L, updatedUser.getId());
        assertEquals("John Doe", updatedUser.getName());
        assertEquals("email@email.com", updatedUser.getEmail());
        assertNotNull(updatedUser.getPassword());
        assertNotNull(updatedUser.getDateOfBirth());
        assertNotNull(updatedUser.getAddress());
        assertNotNull(updatedUser.getResidues());
        assertFalse(updatedUser.getResidues().isEmpty());
    }

    @Test
    void shouldUpdateUserWithExceptionUserNotFound() {
        User update = User.builder()
                .name("John Doe")
                .email("email@email.com")
                .build();
        UserRequest request = new UserRequest(update);

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.userService.update(1L, request));
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

        assertThrows(ConflictException.class, () -> this.userService.update(1L, request));
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

        assertThrows(NotFoundException.class, () -> this.userService.delete(1L));
    }
}
