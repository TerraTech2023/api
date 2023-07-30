package com.terratech.api.controllers;

import com.terratech.api.dto.collector.CollectorResponse;
import com.terratech.api.dto.collector.CollectorUpdate;
import com.terratech.api.services.CollectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/v1/collectors")
@RequiredArgsConstructor
public class CollectorController {

    private final CollectorService collectorService;

    @GetMapping("/{id}")
    public ResponseEntity<CollectorResponse> getOne(@PathVariable("id") Long id) {
        CollectorResponse collector = this.collectorService.findById(id);
        return new ResponseEntity<>(collector,OK);
    }

    @GetMapping
    public ResponseEntity<List<CollectorResponse>> getAll() {
        List<CollectorResponse> collectors = this.collectorService.findAll();
        return new ResponseEntity<>(collectors, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        this.collectorService.delete(id);
        return new ResponseEntity<>("SUCCESSFULLY DELETED COLLECTOR",OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody CollectorUpdate collector) {
        this.collectorService.update(id, collector);
        return new ResponseEntity<>("SUCCESSFULLY UPDATED COLLECTOR", OK);
    }

}
