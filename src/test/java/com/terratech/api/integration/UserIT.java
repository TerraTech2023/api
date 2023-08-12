package com.terratech.api.integration;

import com.terratech.api.dto.user.UserRequest;
import com.terratech.api.dto.user.UserResponse;
import com.terratech.api.error.ErrorResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class UserIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    @Sql(value = "/data_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldGetOneUser() {
        LocalDate birthDate = LocalDate.of(2000, 10, 10);
        UserResponse user = restTemplate.getForObject("/v1/users/1", UserResponse.class);
        System.out.println(user);
        assertNotNull(user);
        assertEquals("John Doe", user.name());
        assertEquals("john@doe.com", user.email());
        assertEquals("123456", user.password());
        assertEquals(birthDate, user.dateOfBirth());
        assertEquals("12345678", user.address().getCep());
        assertEquals("92", user.address().getNumber());
        assertEquals(0, user.residues().size());

    }

    @Test
    @Order(2)
    void shouldPutUser() {
        UserRequest request =
                new UserRequest("nome updated", null, null, null, "12345378", "12");

        restTemplate.put("/v1/users/1", request);

        UserResponse user = restTemplate.getForObject("/v1/users/1", UserResponse.class);

        System.out.println(user);

        assertNotNull(user);
        assertNotNull(user.email());
        assertNotNull(user.password());
        assertNotNull(user.dateOfBirth());
        assertNotNull(user.address());
        assertNotNull(user.residues());
        assertEquals("12345378", user.address().getCep());
        assertEquals("12", user.address().getNumber());
        assertEquals("nome updated", user.name());
    }

    @Test
    @Order(3)
    void shouldDeleteUser() {
        restTemplate.delete("/v1/users/1", String.class);
        ErrorResponse user = restTemplate.getForObject("/v1/users/1", ErrorResponse.class);

        assertEquals("User not found", user.message());
        assertEquals(404, user.status());
    }

}
