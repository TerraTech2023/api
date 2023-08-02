package com.terratech.api.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"message", "status", "timestamp"})
public record ErrorResponse(
        String message,
        Integer status,
        String timestamp
) {
}
