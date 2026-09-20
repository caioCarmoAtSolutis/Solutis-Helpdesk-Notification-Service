package com.solutis.helpdesk.service.notification.message.rabbitmq.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TicketServiceListener {
    //@Value("${rabbitmq.ticket.created.routing-key}")
    //private String ticketCreatedRoutingkey;

    //@Value("${rabbitmq.ticket.assigned.routing-key}")
    //private String ticketAssignedRoutingkey;

    //@Value("${rabbitmq.ticket.status-changed.routing-key}")
    //private String ticketStatusChangedRoutingkey;

    @RabbitListener(queues = "ticket.created")
    public void ListenQueueTicketCreated(Message message) {
        System.out.println("[TicketServiceListener] Received a new ticket created message" + message);
    }

    @RabbitListener(queues = "ticket.assigned")
    public void ListenQueueTicketAssigned(Message message) {
        System.out.println("[TicketServiceListener] Received a new ticket assigned message" + message);
    }

    @RabbitListener(queues = "ticket.status-changed")
    public void ListenQueueTicketStatusChanged(Message message) {
        System.out.println("[TicketServiceListener] Received a new ticket status changed message" + message);
    }
}
