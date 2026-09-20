package com.solutis.helpdesk.service.notification.repository;

import com.solutis.helpdesk.service.notification.domain.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}
