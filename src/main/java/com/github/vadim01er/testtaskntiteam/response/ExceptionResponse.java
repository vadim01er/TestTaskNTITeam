package com.github.vadim01er.testtaskntiteam.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Response class of exception.
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema
public class ExceptionResponse {
    private final int status;
    private final String title;
    private final List<String> errors;

    /**
     * Instantiates a new Exception response.
     *
     * @param status {@link HttpStatus} code
     * @param title  short information about the error
     * @param errors the errors
     */
    public ExceptionResponse(HttpStatus status, String title, List<String> errors) {
        this.status = status.value();
        this.title = title;
        this.errors = errors;
    }

    public ExceptionResponse(HttpStatus status, String title) {
        this.status = status.value();
        this.title = title;
        this.errors = null;
    }
}
