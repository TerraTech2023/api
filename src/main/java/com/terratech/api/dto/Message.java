package com.terratech.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Message(
        @JsonProperty("message")
        String message
) {
}
