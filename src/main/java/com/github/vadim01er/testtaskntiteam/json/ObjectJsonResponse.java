package com.github.vadim01er.testtaskntiteam.json;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ObjectJsonResponse extends JsonResponse {
    private Object item;

    public ObjectJsonResponse(HttpStatus status, Object item) {
        super(status);
        this.item = item;
    }
}
