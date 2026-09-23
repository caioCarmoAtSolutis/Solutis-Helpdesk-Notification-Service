package com.solutis.helpdesk.service.notification.controller;

import com.solutis.helpdesk.service.notification.domain.dto.NotificationData;
import com.solutis.helpdesk.service.notification.infrastructure.exception.ExceptionMessage;
import com.solutis.helpdesk.service.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @Tag(name = "List Notifications")
    @Operation(summary = "Get notifications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list of notifications",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationData.class)) }) })
    @GetMapping
    public ResponseEntity<Page<NotificationData>> getNotifications(@PageableDefault(size = 20) Pageable pageable) {
        Page<NotificationData> page = notificationService.getNotifications(pageable);
        return ResponseEntity.ok().body(page);
    }

    @Tag(name = "List Notifications")
    @Operation(summary = "Get notification by ticketId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the notification for specified id",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationData.class)) }),
            @ApiResponse(responseCode = "400", description = "Can't find notification with specified id",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class)) }) })
    @GetMapping("/{id}")
    public ResponseEntity<NotificationData> getNotification(@PathVariable UUID id) {
        NotificationData notification = notificationService.getNotification(id);
        return ResponseEntity.ok().body(notification);
    }

    @Tag(name = "List Notifications")
    @Operation(summary = "Get notifications by ticketId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list of notifications for specified ticketId",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationData.class)) }) })
    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<Page<NotificationData>> getNotificationsByTicketId(@PageableDefault(size = 20) Pageable pageable, @PathVariable UUID ticketId) {
        Page<NotificationData> page = notificationService.getNotificationsByTicketId(pageable, ticketId);
        return ResponseEntity.ok().body(page);
    }
}
