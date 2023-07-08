package com.terratech.api.repository;

import com.terratech.api.model.Address;
import com.terratech.api.model.Residue;
import com.terratech.api.model.User;
import com.terratech.api.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    @Test
    void shouldFindByIdReturnUser() {

        User userEntity = entityManager.persist(User.builder()
                .name("Teste")
                .email("teste@gmail.com")
                .password("teste123@")
                .dateOfBirth(LocalDate.of(2000, 10, 10))
                .address(new Address("12345678", "92"))
                .residues(List.of(new Residue()))
                .build()
        );

        Optional<User> user = this.repository.findById(1L);

        assertNotNull(user.get());
        assertEquals(userEntity.getId(), user.get().getId());
        assertEquals(userEntity.getName(), user.get().getName());
        assertEquals(userEntity.getEmail(), user.get().getEmail());
        assertEquals(userEntity.getPassword(), user.get().getPassword());
        assertEquals(userEntity.getDateOfBirth(), user.get().getDateOfBirth());
        assertEquals(userEntity.getAddress(), user.get().getAddress());
        assertEquals(userEntity.getResidues(), user.get().getResidues());
        assertEquals(userEntity, user.get());
    }

}
