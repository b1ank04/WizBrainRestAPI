package com.blank04.model.dto;

import lombok.Data;

@Data
public class SolveResponseDto {
    private String request;
    private String response;
    private String error;

    public SolveResponseDto(String request, String response) {
        this.request = request;
        this.response = response;
    }

    public SolveResponseDto(String error) {
        this.error = error;
    }
}
