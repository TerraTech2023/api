package com.terratech.api.services.implementaions;

import com.terratech.api.dto.collector.CollectorResponse;
import com.terratech.api.dto.collector.CollectorUpdate;
import com.terratech.api.model.Collector;
import com.terratech.api.repositories.CollectorRepository;
import com.terratech.api.services.CollectorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectorServiceImp implements CollectorService {

    private final CollectorRepository collectorRepository;

    private final ModelMapper mapper;

    @Override
    public CollectorResponse findById(Long id) {
        Collector collector = this.collectorAlreadyExists(id);
        return new CollectorResponse(collector);
    }

    @Override
    public List<CollectorResponse> findAll() {
        return this.collectorRepository.findAll()
                .stream()
                .map(CollectorResponse::new)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Collector collector = this.collectorAlreadyExists(id);
        this.collectorRepository.delete(collector);
    }

    @Override
    public void update(Long id, CollectorUpdate collector) {
        Collector collectorToUpdate = this.collectorAlreadyExists(id);
        this.mapper.map(collector, collectorToUpdate);
        this.collectorRepository.save(collectorToUpdate);
    }

    private Collector collectorAlreadyExists(Long id) {
        return this.collectorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Collector not found"));
    }
}
