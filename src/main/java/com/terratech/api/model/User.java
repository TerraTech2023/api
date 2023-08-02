package com.terratech.api.model;

import com.terratech.api.model.Abstract.Person;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends Person {
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "user")
    private List<Residue> residues;
}
