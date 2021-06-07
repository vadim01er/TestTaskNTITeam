package com.github.vadim01er.testtaskntiteam.json;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExeptionJsonResponse extends JsonResponse {

    private final String title;
    private final String detail;

    public ExeptionJsonResponse(HttpStatus httpStatus, String title, String detail) {
        super(httpStatus);
        this.title = title;
        this.detail = detail;
    }
}
