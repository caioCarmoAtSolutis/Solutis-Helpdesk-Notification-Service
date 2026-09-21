package com.solutis.helpdesk.service.notification.domain.dto;

import com.solutis.helpdesk.service.notification.domain.model.Status;
import jakarta.validation.constraints.NotNull;

public record StatusMessageData(
        @NotNull
        Status status
) {
}
