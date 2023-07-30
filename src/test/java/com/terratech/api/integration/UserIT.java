package com.terratech.api.integration;

import com.terratech.api.dto.user.UserRequest;
import com.terratech.api.exception.ErrorResponse;
import com.terratech.api.model.User;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

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
    void shouldPostUser() {
        LocalDate birthDate = LocalDate.of(2000, 10, 10);
        UserRequest request =
                new UserRequest("John Doe", "john@doe.com", "123456", birthDate, "12345678", "92");

        User post = restTemplate.postForObject("/v1/users", request, User.class);

        System.out.println(post);

        assertNotNull(post);
        assertNotNull(post.getId());
        assertEquals("John Doe", post.getName());
        assertEquals("john@doe.com", post.getEmail());
        assertEquals("123456", post.getPassword());
        assertEquals(birthDate, post.getDateOfBirth());
        assertEquals("12345678", post.getAddress().getCep());
        assertEquals("92", post.getAddress().getNumber());
        assertEquals(0, post.getResidues().size());
    }

    @Test
    @Order(2)
    void shouldGetOneUser() {
        LocalDate birthDate = LocalDate.of(2000, 10, 10);
        User user = restTemplate.getForObject("/v1/users/1", User.class);
        System.out.println(user);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@doe.com", user.getEmail());
        assertEquals("123456", user.getPassword());
        assertEquals(birthDate, user.getDateOfBirth());
        assertEquals("12345678", user.getAddress().getCep());
        assertEquals("92", user.getAddress().getNumber());
        assertEquals(0, user.getResidues().size());

    }

    @Test
    @Order(3)
    void shouldPutUser() {
        UserRequest request =
                new UserRequest("nome updated", null, null, null, "12345378", "12");

        restTemplate.put("/v1/users/1", request, User.class);

        User user = restTemplate.getForObject("/v1/users/1", User.class);

        System.out.println(user);

        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getEmail());
        assertNotNull(user.getPassword());
        assertNotNull(user.getDateOfBirth());
        assertNotNull(user.getAddress());
        assertNotNull(user.getResidues());
        assertEquals("12345378", user.getAddress().getCep());
        assertEquals("12", user.getAddress().getNumber());
        assertEquals("nome updated", user.getName());
    }

    @Test
    @Order(4)
    void shouldDeleteUser() {
        restTemplate.delete("/v1/users/1", String.class);
        ErrorResponse user = restTemplate.getForObject("/v1/users/1", ErrorResponse.class);

        assertEquals("User not found", user.message());
        assertEquals(404, user.status());
    }

}
