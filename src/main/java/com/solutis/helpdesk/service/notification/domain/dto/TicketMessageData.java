package com.solutis.helpdesk.service.notification.domain.dto;

import com.solutis.helpdesk.service.notification.domain.model.Category;
import com.solutis.helpdesk.service.notification.domain.model.Priority;
import com.solutis.helpdesk.service.notification.domain.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public record TicketMessageData(
        @NotNull
        UUID id,

        UUID technicianId,

        @NotNull
        UUID customerId,

        @NotBlank
        @Size(max = 50)
        String title,

        @NotBlank
        @Size(max = 250)
        String description,

        @NotNull
        Priority priority,

        @NotNull
        Status status,

        @NotNull
        Category category,

        @NotNull
        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
