package com.Images.Image.models;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Response {
    private Object image;
    private HttpStatus status;

    public Response(String image, HttpStatus status) {
        this.image = image;
        this.status = status;
    }

    public Response(byte[] image, HttpStatus status) {
        this.image = image;
        this.status = status;
    }
}
