package com.github.vadim01er.testtaskntiteam.json;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ListJsonResponse extends JsonResponse{

    private final List<?> items;

    public ListJsonResponse(HttpStatus httpStatus, List<?> items) {
        super(httpStatus);
        this.items = items;
    }
}
