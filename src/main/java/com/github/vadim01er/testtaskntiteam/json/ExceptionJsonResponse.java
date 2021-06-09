package com.github.vadim01er.testtaskntiteam.json;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionJsonResponse extends JsonResponse {

    private final String title;
    private final String detail;

    public ExceptionJsonResponse(HttpStatus httpStatus, String title, String detail) {
        super(httpStatus);
        this.title = title;
        this.detail = detail;
    }
}
