package com.solutis.helpdesk.service.notification.domain.model;

import com.solutis.helpdesk.service.notification.domain.dto.TicketMessageData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NOTIFICATIONS")
public class Notification {
    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "TICKET_ID")
    private UUID ticketId;

    @Column(name = "TECHNICIAN_ID")
    private UUID technicianId;

    @Column(name = "CUSTOMER_ID")
    private UUID customerId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "MESSAGE")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRIORITY")
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY")
    private Category category;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    public Notification(TicketMessageData data, String message) {
        this.id = UUID.randomUUID();
        this.ticketId = data.id();
        this.technicianId = data.technicianId();
        this.customerId = data.customerId();
        this.title = data.title();
        this.description = data.description();
        this.message = message;
        this.priority = Priority.valueOf(data.priority().toString());
        this.status = Status.valueOf(data.status().toString());
        this.category = Category.valueOf(data.category().toString());
        this.createdAt = LocalDateTime.now();
    }
}