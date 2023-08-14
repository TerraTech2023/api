package com.terratech.api.repository;

import com.terratech.api.model.Collector;
import com.terratech.api.repositories.CollectorRepository;
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
public class CollectorRepositoryTest {

    @Autowired
    private CollectorRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private Collector collectorEntity;

    @BeforeEach
    void setup() {
        collectorEntity = entityManager.persist(
                Collector.builder()
                        .name("Teste")
                        .email("teste@gmail.com")
                        .password("teste123@")
                        .dateOfBirth(LocalDate.of(2000, 10, 10))
                        .build()
        );
    }

    @Test
    void shouldFindByIdReturnCollector() {
        Optional<Collector> collector = this.repository.findById(collectorEntity.getId());

        assertNotNull(collector);
        assertTrue(collector.isPresent());
        assertEquals(collectorEntity.getId(), collector.get().getId());
        assertEquals(collectorEntity.getName(), collector.get().getName());
        assertEquals(collectorEntity.getEmail(), collector.get().getEmail());
        assertEquals(collectorEntity.getPassword(), collector.get().getPassword());
        assertEquals(collectorEntity.getDateOfBirth(), collector.get().getDateOfBirth());
        assertEquals(collectorEntity, collector.get());
    }

    @Test
    void shouldFindAllCollector(){
        List<Collector> collectors = this.repository.findAll();

        assertNotNull(collectors);
        assertFalse(collectors.isEmpty());
        assertEquals(1, collectors.size());
    }

    @Test
    void  shouldSavedCollector() {
        Collector collector = Collector.builder()
                .name("Teste")
                .email("teste321@gmail.com")
                .password("teste321")
                .dateOfBirth(LocalDate.of(2000, 10, 10))
                .build();

        this.repository.save(collector);

        List<Collector> collectors = this.repository.findAll();

        assertEquals(2, collectors.size());
    }


    @Test
    void shouldDeleteCollector() {
        this.repository.delete(collectorEntity);

        List<Collector> collectors = this.repository.findAll();
        System.out.println(collectors);

        assertEquals(0, collectors.size());
    }

    @Test
    void  shouldUpdateCollector() {
        Collector collector = this.repository.findById(collectorEntity.getId()).orElseThrow();
        collector.setEmail("updated@gmail.com");
        collector.setName("updated");

        entityManager.persist(collector);
        entityManager.flush();

        Collector updatedCollector = this.repository.findById(collectorEntity.getId()).orElseThrow();
        assertEquals("updated@gmail.com", updatedCollector.getEmail());
        assertEquals("updated", updatedCollector.getName());
    }
}
