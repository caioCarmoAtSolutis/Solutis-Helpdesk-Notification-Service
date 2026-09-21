package com.solutis.helpdesk.service.notification.service;

import com.solutis.helpdesk.service.notification.domain.dto.NotificationData;
import com.solutis.helpdesk.service.notification.domain.dto.TicketMessageData;
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

    public Page<NotificationData> getNotifications(Pageable pageable) {
        return notificationRepository.findAll(pageable).map(NotificationData::new);
    }

    public NotificationData getNotification(UUID id) {
        return notificationRepository.findById(id).map(NotificationData::new).orElseThrow(EntityNotFoundException::new);
    }

    public void createNotification(TicketMessageData data, String message) {
        var notification = new Notification(data, message);
        notificationRepository.save(notification);
    }

    public Page<NotificationData> getNotificationsByTicketId(Pageable pageable, UUID ticketId) {
        return notificationRepository.findAllByTicketId(pageable, ticketId).map(NotificationData::new);
    }
}
