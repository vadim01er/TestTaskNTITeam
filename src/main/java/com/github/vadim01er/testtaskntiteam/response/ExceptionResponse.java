package com.github.vadim01er.testtaskntiteam.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Response class of exception.
 */
@Getter
@AllArgsConstructor
public class ExceptionResponse {
    private final int status;
    private final String title;
    private final String detail;

    /**
     * Instantiates a new Exception response.
     *
     * @param status {@link HttpStatus} code
     * @param title  short information about the error
     * @param detail detailed information about the error
     */
    public ExceptionResponse(HttpStatus status, String title, String detail) {
        this.status = status.value();
        this.title = title;
        this.detail = detail;
    }
}
