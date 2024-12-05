package com.clutchacademy.user_service.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FailResponse<T> {
    @JsonProperty
    private String status;
    @JsonProperty
    private String message;
    @JsonProperty
    private List<T> errors;

    public FailResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
