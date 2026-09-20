package com.solutis.helpdesk.service.notification.controller;

import com.solutis.helpdesk.service.notification.domain.model.Notification;
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
    public ResponseEntity<Page<Notification>> getNotifications(@PageableDefault(size = 10) Pageable pageable) {
        Page<Notification> page = notificationService.getNotifications(pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotification(@PathVariable UUID id) {
        Notification notification = notificationService.getNotification(id);
        return ResponseEntity.ok().body(notification);
    }
}
