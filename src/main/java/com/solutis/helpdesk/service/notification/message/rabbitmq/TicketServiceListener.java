package com.solutis.helpdesk.service.notification.message.rabbitmq;

import com.solutis.helpdesk.service.notification.domain.dto.TicketMessageData;
import com.solutis.helpdesk.service.notification.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketServiceListener {
    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = "${rabbitmq.ticket.created.routing-key}")
    public void ListenQueueTicketCreated(TicketMessageData data) {
        String message = "Ticket with id " + data.id() + " created!";
        notificationService.createNotification(data, message);
    }

    @RabbitListener(queues = "${rabbitmq.ticket.assigned.routing-key}")
    public void ListenQueueTicketAssigned(TicketMessageData data) {
        String message = "Technician with id " + data.id() + " assigned!";
        notificationService.createNotification(data,  message);
    }

    @RabbitListener(queues = "${rabbitmq.ticket.status-changed.routing-key}")
    public void ListenQueueTicketStatusChanged(TicketMessageData data) {
        String message = "Ticket with id " + data.id() + " status changed to " + data.status().toString();
        notificationService.createNotification(data,   message);
    }
}
