package com.elshanaliyev.aliyevauto.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBrandNotFound(BrandNotFoundException ex) {
        return notFound(ex.getMessage());
    }

    @ExceptionHandler(EngineNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEngineNotFound(EngineNotFoundException ex) {
        return notFound(ex.getMessage());
    }

    @ExceptionHandler(ColorNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleColorNotFound(ColorNotFoundException ex) {
        return notFound(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException ex) {
        return notFound(ex.getMessage());
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCarNotFound(CarNotFoundException ex) {
        return notFound(ex.getMessage());
    }

    @ExceptionHandler(NewCarsNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNewCarsNotFound(NewCarsNotFoundException ex) {
        return notFound(ex.getMessage());
    }

    @ExceptionHandler(ServiceTImeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleServiceTimeNotFound(ServiceTImeNotFoundException ex) {
        return notFound(ex.getMessage());
    }

    @ExceptionHandler(BrandAlreadyExist.class)
    public ResponseEntity<Map<String, String>> handleBrandAlreadyExist(BrandAlreadyExist ex) {
        return conflict(ex.getMessage());
    }

    @ExceptionHandler(EngineAlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handleEngineAlreadyExist(EngineAlreadyExistException ex) {
        return conflict(ex.getMessage());
    }

    @ExceptionHandler(ColorAlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handleColorAlreadyExist(ColorAlreadyExistException ex) {
        return conflict(ex.getMessage());
    }

    @ExceptionHandler(UserCantDeleted.class)
    public ResponseEntity<Map<String, String>> handleUserCantDeleted(UserCantDeleted ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(WrongNumberForm.class)
    public ResponseEntity<Map<String, String>> handleWrongNumberForm(WrongNumberForm ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", msg));
    }

    private static ResponseEntity<Map<String, String>> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", message));
    }

    private static ResponseEntity<Map<String, String>> conflict(String message) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", message));
    }
}
