package com.terratech.api.dto.collector;

import com.terratech.api.model.Collector;

public record CollectorUpdate(
        String name,
        String email,
        String password,
        String confirmPassword
) {
    public Collector toModel() {
        return Collector.builder()
                .name(this.name)
                .email(this.email())
                .password(this.password())
                .build();

    }
}
