package com.terratech.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terratech.api.dto.UserRequest;
import com.terratech.api.exception.ConflictException;
import com.terratech.api.exception.ExceptionController;
import com.terratech.api.exception.NotFoundException;
import com.terratech.api.model.Address;
import com.terratech.api.model.Residue;
import com.terratech.api.model.User;
import com.terratech.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    void shouldReturnUser() throws Exception {

        when(userService.findById(anyLong())).thenReturn(user);

        mockMvc.perform(get("/v1/users/1")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Teste"));
    }

    @Test
    void shouldReturnNotFound() throws Exception {

        when(userService.findById(anyLong()))
                .thenThrow(new NotFoundException("User not found"));

        mockMvc.perform(get("/v1/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("User not found"));
    }

    @Test
    void shouldCreateUser() throws Exception {

        when(userService.findById(anyLong())).thenReturn(user);

        var request = new UserRequest(user);

        var json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/v1/users")
                        .accept("application/json")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldCreateReturnConflict() throws Exception {

        when(userService.create(any(UserRequest.class)))
                .thenThrow(new ConflictException("Email already exists"));

        var request = new UserRequest(user);

        var json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/v1/users")
                        .accept("application/json")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isConflict());
    }

}
