package com.shortener.url_shortener_sb.errors;


import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static String traceId() {
        return MDC.get("traceId");
    }

    private ApiError apiError(HttpServletRequest req,HttpStatus status, String message) {
        return new ApiError(
                Instant.now(),
                req.getRequestURI(),
                status.value(),
                message,
                traceId());
    }

    //Validation errors from @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(err -> err.getField() +": " + err.getDefaultMessage())
                .orElse("Validation error");

        return ResponseEntity.badRequest().body(apiError(req,HttpStatus.BAD_REQUEST,message));
    }

    //Bad Json and Wrong type etc.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleBadJson(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(apiError(request, HttpStatus.BAD_REQUEST,"Malformed Json Request"));
    }

    //Duplicate Username/email etc.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArg(IllegalArgumentException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(apiError(request,HttpStatus.CONFLICT,ex.getMessage()));
    }

    //Not Authenticated
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuth(AuthenticationException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError(request,HttpStatus.UNAUTHORIZED,"Unauthorized"));
    }

    //Authenticated But not Allowed
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex,HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError(request,HttpStatus.FORBIDDEN,"Forbidden"));
    }

    //catch-All
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex,HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError(request, HttpStatus.INTERNAL_SERVER_ERROR,"Internal Server Error"));
    }
}
