package com.blank04.model;

import lombok.Data;

@Data
public class ResultDto {
    private String request;
    private String response;
    private String error;

    public ResultDto(String request, String response) {
        this.request = request;
        this.response = response;
    }

    public ResultDto(String error) {
        this.error = error;
    }
}
