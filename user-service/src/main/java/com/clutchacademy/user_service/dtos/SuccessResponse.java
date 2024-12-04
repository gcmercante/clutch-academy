package com.clutchacademy.user_service.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> {
    @JsonProperty
    private String status;
    @JsonProperty
    private ResponseData<T> data;

    public SuccessResponse (String status, ResponseData<T> data) {
        this.status = status;
        this.data = data;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ResponseData<T> {
        @JsonProperty
        private String id;
        @JsonProperty
        private T resource;

        public ResponseData (T resource) {
            this.resource = resource;
        }
    }
}
