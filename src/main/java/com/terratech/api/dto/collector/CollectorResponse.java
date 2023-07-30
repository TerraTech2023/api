package com.terratech.api.dto.collector;

import com.terratech.api.model.Collector;

import java.time.LocalDate;

public record CollectorResponse(
        String name,
        String email,
        LocalDate dateOfBirth
) {

    public CollectorResponse (Collector collector){
        this(collector.getName(), collector.getEmail(), collector.getDateOfBirth());
    }
    
}
