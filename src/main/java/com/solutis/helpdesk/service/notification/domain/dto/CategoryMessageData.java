package com.solutis.helpdesk.service.notification.domain.dto;

import com.solutis.helpdesk.service.notification.domain.model.Category;
import jakarta.validation.constraints.NotNull;

public record CategoryMessageData(
        @NotNull
        Category category
) {
}
