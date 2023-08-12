package com.terratech.api.error;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"message", "status", "timestamp"})
public record ErrorResponse(
        String message,
        Integer status,
        String timestamp
) {
}
