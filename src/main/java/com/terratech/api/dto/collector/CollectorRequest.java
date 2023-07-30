package com.terratech.api.dto.collector;

import com.terratech.api.model.Collector;

import java.time.LocalDate;

public record CollectorRequest(
        String name,
        String email,
        String password,
        String confirmPassword,
        LocalDate dateOfBirth
) {
    public Collector toModel() {
        return Collector.builder()
                .name(this.name())
                .email(this.email())
                .password(this.password())
                .dateOfBirth(this.dateOfBirth())
                .build();
    }
}
