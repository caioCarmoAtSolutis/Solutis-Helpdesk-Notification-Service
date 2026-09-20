package com.solutis.helpdesk.service.notification.domain.model;

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

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
}