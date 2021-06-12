package com.github.vadim01er.testtaskntiteam.exception;

import com.github.vadim01er.testtaskntiteam.response.ExceptionResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to handler the exception.
 * <br>
 * (use annotation EnableWebMvc for work a custom {@link ExceptionHandler})
 */
@EnableWebMvc
@RestControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle body of request is not readable.
     *
     * @param ex      {@link HttpMessageNotReadableException}
     * @param headers {@link HttpHeaders}
     * @param status  {@link HttpStatus}
     * @param request {@link HttpRequest}
     * @return {@link ResponseEntity} with {@link ExceptionResponse}
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ExceptionResponse body =
                new ExceptionResponse(status, "Invalid json in body");
        return new ResponseEntity<>(body, headers, status);
    }

    /**
     * Handle argument not valid.
     *
     * @param ex      {@link MethodArgumentNotValidException}
     * @param headers {@link HttpHeaders}
     * @param status  {@link HttpStatus}
     * @param request {@link HttpRequest}
     * @return {@link ResponseEntity} with {@link ExceptionResponse}
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        List<String> errors =
                ex.getBindingResult().getFieldErrors().stream()
                        .map(fieldError ->
                                fieldError.getField() + " -> " + fieldError.getDefaultMessage())
                        .collect(Collectors.toList());
        ExceptionResponse body = new ExceptionResponse(status, "Argument not valid", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    /**
     * Handle a bad path name in request.
     *
     * @param ex      {@link TypeMismatchException}
     * @param headers {@link HttpHeaders}
     * @param status  {@link HttpStatus}
     * @param request {@link HttpRequest}
     * @return {@link ResponseEntity} with {@link ExceptionResponse}
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        String format = "The parameter '%s' of value '%s' could not be converted to type '%s'";

        ExceptionResponse body =
                new ExceptionResponse(status,
                        String.format(
                                format,
                                ex.getPropertyName(),
                                ex.getValue(),
                                ex.getRequiredType())
                );
        return new ResponseEntity<>(body, headers, status);
    }

    /**
     * Handle a not found endpoint handler in controller.
     *
     * @param ex      {@link NoHandlerFoundException}
     * @param headers {@link HttpHeaders}
     * @param status  {@link HttpStatus}
     * @param request {@link HttpRequest}
     * @return {@link ResponseEntity} with {@link ExceptionResponse}
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ExceptionResponse body = new ExceptionResponse(
                status,
                "Endpoint not found on this path: \"" + ex.getRequestURL() + "\""
        );
        return new ResponseEntity<>(body, headers, status);
    }

    /**
     * Handle lord not found exception response entity.
     *
     * @param ex the {@link Exception}
     * @return the {@link ResponseEntity} with {@link ExceptionResponse}
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(
            {LordNotFoundException.class, PlanetNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleLordNotFoundException(
            Exception ex
    ) {
        ExceptionResponse body = new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body);
    }

    /**
     * Method handler all default exception handler.
     *
     * @param ex {@link Exception}
     * @return {@link ResponseEntity} with {@link ExceptionResponse}
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(
            Exception ex
    ) {
        ExceptionResponse body = new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                "something went wrong, this is the default server response to "
                        + "an error not specifically described"
        );
        return ResponseEntity.badRequest().body(body);
    }

}
