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
        System.out.println("[TicketServiceListener] Received a new ticket created message" + data);
    }

    @RabbitListener(queues = "${rabbitmq.ticket.assigned.routing-key}")
    public void ListenQueueTicketAssigned(TicketMessageData data) {
        System.out.println("[TicketServiceListener] Received a new ticket assigned message" + data);
    }

    @RabbitListener(queues = "${rabbitmq.ticket.status-changed.routing-key}")
    public void ListenQueueTicketStatusChanged(TicketMessageData data) {
        System.out.println("[TicketServiceListener] Received a new ticket status changed message" + data);
    }
}
