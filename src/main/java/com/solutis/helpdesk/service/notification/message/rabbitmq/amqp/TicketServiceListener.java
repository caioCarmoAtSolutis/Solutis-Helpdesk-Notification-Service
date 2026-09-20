package com.solutis.helpdesk.service.notification.message.rabbitmq.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TicketServiceListener {

    @RabbitListener(queues = "${rabbitmq.ticket.created.routing-key}")
    public void ListenQueueTicketCreated(Message message) {
        System.out.println("[TicketServiceListener] Received a new ticket created message" + message);
    }

    @RabbitListener(queues = "${rabbitmq.ticket.assigned.routing-key}")
    public void ListenQueueTicketAssigned(Message message) {
        System.out.println("[TicketServiceListener] Received a new ticket assigned message" + message);
    }

    @RabbitListener(queues = "${rabbitmq.ticket.status-changed.routing-key}")
    public void ListenQueueTicketStatusChanged(Message message) {
        System.out.println("[TicketServiceListener] Received a new ticket status changed message" + message);
    }
}
