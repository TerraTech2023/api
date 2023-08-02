package com.terratech.api.repositories;

import com.terratech.api.model.Residue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidueRepository extends JpaRepository<Residue, Long> {
}
