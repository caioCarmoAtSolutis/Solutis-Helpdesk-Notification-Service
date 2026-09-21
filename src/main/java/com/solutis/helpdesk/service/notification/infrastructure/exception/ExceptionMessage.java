package com.solutis.helpdesk.service.notification.infrastructure.exception;

import jakarta.validation.constraints.NotNull;

public record ExceptionMessage(
        @NotNull
        String message
) {
}
