package com.solutis.helpdesk.service.notification.service;

import com.solutis.helpdesk.service.notification.domain.model.Notification;
import com.solutis.helpdesk.service.notification.repository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public Page<Notification> getNotifications(Pageable pageable) {
        return notificationRepository.findAll(pageable);
    }

    public Notification getNotification(UUID id) {
        return notificationRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
