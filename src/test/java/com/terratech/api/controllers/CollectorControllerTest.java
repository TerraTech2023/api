package com.terratech.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class})
@WebMvcTest(UserController.class)
class CollectorControllerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldGetOne() {
    }

    @Test
    void shouldGetAll() {
    }

    @Test
    void shouldDeleted() {
    }

    @Test
    void shouldUpdated() {
    }
}