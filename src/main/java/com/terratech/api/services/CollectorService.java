package com.terratech.api.services;

import com.terratech.api.dto.collector.CollectorResponse;
import com.terratech.api.dto.collector.CollectorUpdate;

import java.util.List;

public interface CollectorService {
    CollectorResponse findById(Long id);

    List<CollectorResponse> findAll();

    void delete(Long id);

    void update(Long id, CollectorUpdate collector);
}
