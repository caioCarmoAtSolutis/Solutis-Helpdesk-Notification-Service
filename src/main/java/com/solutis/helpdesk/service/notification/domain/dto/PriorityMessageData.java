package com.solutis.helpdesk.service.notification.domain.dto;

import com.solutis.helpdesk.service.notification.domain.model.Priority;
import jakarta.validation.constraints.NotNull;

public record PriorityMessageData(
        @NotNull
        Priority priority
) {
}
