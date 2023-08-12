package com.terratech.api.model.Abstract;

import com.terratech.api.model.enums.Role;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@MappedSuperclass

public abstract class Person {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private Role role;
}
