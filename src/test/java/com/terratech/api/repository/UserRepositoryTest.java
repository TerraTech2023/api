package com.terratech.api.repository;

import com.terratech.api.model.Address;
import com.terratech.api.model.Residue;
import com.terratech.api.model.User;
import com.terratech.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    private User userEntity;

    @BeforeEach
    void setup() {
        userEntity = entityManager.persist(User.builder()
                .name("Teste")
                .email("teste@gmail.com")
                .password("teste123@")
                .dateOfBirth(LocalDate.of(2000, 10, 10))
                .address(new Address("12345678", "92"))
                .residues(List.of(new Residue()))
                .build()
        );
    }

    @Test
    void shouldFindByIdReturnUser() {

        Optional<User> user = this.repository.findById(userEntity.getId());

        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals(userEntity.getId(), user.get().getId());
        assertEquals(userEntity.getName(), user.get().getName());
        assertEquals(userEntity.getEmail(), user.get().getEmail());
        assertEquals(userEntity.getPassword(), user.get().getPassword());
        assertEquals(userEntity.getDateOfBirth(), user.get().getDateOfBirth());
        assertEquals(userEntity.getAddress(), user.get().getAddress());
        assertEquals(userEntity.getResidues(), user.get().getResidues());
        assertEquals(userEntity, user.get());
    }

    @Test
    void shouldFindByEmailReturnUser() {
        Optional<User> user = this.repository.findByEmail(userEntity.getEmail());

        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals(userEntity.getId(), user.get().getId());
        assertEquals(userEntity.getName(), user.get().getName());
        assertEquals(userEntity.getEmail(), user.get().getEmail());
        assertEquals(userEntity.getPassword(), user.get().getPassword());
        assertEquals(userEntity.getDateOfBirth(), user.get().getDateOfBirth());
        assertEquals(userEntity.getAddress(), user.get().getAddress());
        assertEquals(userEntity.getResidues(), user.get().getResidues());
        assertEquals(userEntity, user.get());
    }

    @Test
    void shouldSaveUser() {
        User user = User.builder()
                .name("Teste")
                .email("teste@gmail.com")
                .password("teste123@")
                .dateOfBirth(LocalDate.of(2000, 10, 10))
                .address(new Address("12345678", "92"))
                .residues(List.of(new Residue()))
                .build();

        this.repository.save(user);

        List<User> select = this.repository.findAll();

        assertEquals(2, select.size());

    }

    @Test
    void shouldUpdateUser() {
        User user = this.repository.findById(userEntity.getId()).orElseThrow();
        user.setName("Teste 2");
        user.setEmail("email@hotmart.com");

        entityManager.persist(user);
        entityManager.flush();

        User updatedUser = this.repository.findById(userEntity.getId()).orElseThrow();
        assertEquals("Teste 2", updatedUser.getName());
        assertEquals("email@hotmart.com", updatedUser.getEmail());
    }

    @Test
    void shouldDeleteUser() {
        User user = this.repository.findById(userEntity.getId()).orElseThrow();
        this.repository.delete(user);
        assertEquals(0, this.repository.findAll().size());
    }
}
