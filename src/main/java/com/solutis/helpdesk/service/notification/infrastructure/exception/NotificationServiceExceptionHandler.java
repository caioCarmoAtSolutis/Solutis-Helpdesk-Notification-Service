package com.solutis.helpdesk.service.notification.infrastructure.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotificationServiceExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handleException(EntityNotFoundException e) {
        var exceptionMessage = new ExceptionMessage(e.getMessage());
        return ResponseEntity.badRequest().body(exceptionMessage);
    }
}
