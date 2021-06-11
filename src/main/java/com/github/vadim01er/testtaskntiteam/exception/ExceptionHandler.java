package com.github.vadim01er.testtaskntiteam.exception;

import com.github.vadim01er.testtaskntiteam.response.ExceptionResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Class to handler the exception.
 * <br>
 * (use annotation EnableWebMvc for work a custom {@link ExceptionHandler})
 */
@EnableWebMvc
@RestControllerAdvice
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
        return ResponseEntity.status(status).body(
                new ExceptionResponse(
                        status,
                        "Unformed json in body",
                        Collections.singletonList("Unformed json in body")
                )
        );
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
        return ResponseEntity.status(status).body(
                new ExceptionResponse(
                        status,
                        "No valid json field: " + ex.getBindingResult().getFieldErrors().stream()
                                .map(FieldError::getField)
                                .collect(Collectors.toSet()),
                        ex.getBindingResult().getFieldErrors().stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.toList())
                )
        );
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
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(status,
                        "Invalid type parameter",
                        Collections.singletonList(
                                String.format(
                                        format,
                                        ex.getPropertyName(),
                                        ex.getValue(),
                                        ex.getRequiredType())
                        )
                )
        );
    }

//    /**
//     * Method handler {@link ConstraintViolationException}.
//     *
//     * @param ex {@link ConstraintViolationException}
//     * @return {@link ResponseEntity} with {@link ExceptionResponse}
//     */
//    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<Object> handleConstraintViolationException(
//            ConstraintViolationException ex
//    ) {
//        return ResponseEntity.badRequest().body(
//                new ExceptionResponse(
//                        HttpStatus.BAD_REQUEST,
//                        ex.getMessage(),
//                        ex.getConstraintViolations().stream()
//                                .map(ConstraintViolation::getExecutableParameters)
//                                .flatMap(objects -> Arrays.stream(objects).map(Object::))
//                                .collect(Collectors.toList())
//                )
//        );
//    }

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
        return ResponseEntity.status(status).body(
                new ExceptionResponse(
                        status,
                        "Endpoint not found",
                        Collections.singletonList(
                                "Endpoint not found on this path: \""
                                        + ex.getRequestURL()
                                        + "\""
                        )
                )
        );
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
//        ex.printStackTrace();
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(
                        HttpStatus.BAD_REQUEST,
                        ex.getMessage(),
                        Collections.singletonList(ex.getMessage())
                )
        );
    }

}