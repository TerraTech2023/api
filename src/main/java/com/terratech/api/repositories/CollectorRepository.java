package com.terratech.api.repositories;

import com.terratech.api.model.Collector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectorRepository extends JpaRepository<Collector, Long> {
}
