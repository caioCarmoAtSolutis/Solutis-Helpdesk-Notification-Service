package com.solutis.helpdesk.service.notification.controller;

import com.solutis.helpdesk.service.notification.domain.dto.NotificationData;
import com.solutis.helpdesk.service.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<Page<NotificationData>> getNotifications(@PageableDefault(size = 20) Pageable pageable) {
        Page<NotificationData> page = notificationService.getNotifications(pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationData> getNotification(@PathVariable UUID id) {
        NotificationData notification = notificationService.getNotification(id);
        return ResponseEntity.ok().body(notification);
    }

    @GetMapping("/{ticketsId}")
    public ResponseEntity<Page<NotificationData>> getNotificationsByTicketId(@PageableDefault(size = 20) Pageable pageable, @PathVariable UUID ticketId) {
        Page<NotificationData> page = notificationService.getNotificationsByTicketId(pageable, ticketId);
        return ResponseEntity.ok().body(page);
    }
}
