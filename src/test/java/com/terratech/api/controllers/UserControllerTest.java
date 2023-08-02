package com.terratech.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terratech.api.dto.user.UserRequest;
import com.terratech.api.dto.user.UserResponse;
import com.terratech.api.exception.ConflictException;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        when(userService.findById(anyLong())).thenReturn(new UserResponse(user));

        String userWriteAsString = objectMapper.writeValueAsString(new UserResponse(user));

        mockMvc.perform(get("/v1/users/1")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(userWriteAsString));
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
    void shouldUpdateUser() throws Exception {

        user.setEmail("update@email.com");

        doNothing().when(userService).update(anyLong(), any(UserRequest.class));

        var request = new UserRequest(user);

        var json = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/v1/users/1")
                        .accept("application/json")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("SUCCESSFULLY UPDATED USER"));
    }

    @Test
    void shouldUpdateReturnNotFound() throws Exception {

        doThrow(new NotFoundException("User not found"))
                .when(userService)
                .update(anyLong(), any(UserRequest.class));

        var request = new UserRequest(user);

        var json = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/v1/users/1")
                        .accept("application/json")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("User not found"));
    }

    @Test
    void shouldUpdateReturnConflict() throws Exception {

        doThrow(new ConflictException("Email already exists"))
                .when(userService)
                .update(anyLong(), any(UserRequest.class));

        var request = new UserRequest(user);

        var json = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/v1/users/1")
                        .accept("application/json")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("message").value("Email already exists"));
    }

    @Test
    void shouldDeleteUser() throws Exception {

        doNothing().when(userService).delete(anyLong());

        mockMvc.perform(delete("/v1/users/1")
                        .accept("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteReturnNotFound() throws Exception {

        doThrow(new NotFoundException("User not found"))
                .when(userService)
                .delete(anyLong());

        mockMvc.perform(delete("/v1/users/1")
                        .accept("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("User not found"));
    }

}
