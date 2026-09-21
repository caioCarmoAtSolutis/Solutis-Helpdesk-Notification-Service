package com.solutis.helpdesk.service.notification.controller;

import com.jayway.jsonpath.JsonPath;
import com.solutis.helpdesk.service.notification.domain.dto.CategoryMessageData;
import com.solutis.helpdesk.service.notification.domain.dto.PriorityMessageData;
import com.solutis.helpdesk.service.notification.domain.dto.StatusMessageData;
import com.solutis.helpdesk.service.notification.domain.dto.TicketMessageData;
import com.solutis.helpdesk.service.notification.domain.model.Category;
import com.solutis.helpdesk.service.notification.domain.model.Priority;
import com.solutis.helpdesk.service.notification.domain.model.Status;
import com.solutis.helpdesk.service.notification.infrastructure.exception.ExceptionMessage;
import com.solutis.helpdesk.service.notification.service.NotificationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional // Automatically rolls back database changes made by MockMvc after each test method
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private JacksonTester<ExceptionMessage> exceptionMessageJson;

    private TicketMessageData buildTicketMessageData(UUID ticketId, UUID customerId, UUID technicianId,
                                                     String title, String description,
                                                     Priority priority, Status status, Category category) {
        return new TicketMessageData(
                ticketId,
                technicianId,
                customerId,
                title,
                description,
                new PriorityMessageData(priority),
                new StatusMessageData(status),
                new CategoryMessageData(category),
                LocalDateTime.now(),
                null);
    }

    private UUID createTestNotification(String message) {
        UUID ticketId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        UUID technicianId = UUID.randomUUID();

        TicketMessageData data = buildTicketMessageData(
                ticketId, customerId, technicianId,
                "Test Ticket", "Test ticket description",
                Priority.LOW, Status.OPEN, Category.HARDWARE);

        notificationService.createNotification(data, message);
        return ticketId;
    }

    private UUID getNotificationIdByTicketId(UUID ticketId) throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                get("/notifications/ticket/{ticketId}", ticketId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        String id = JsonPath.read(response.getContentAsString(), "$.content[0].id");
        return UUID.fromString(id);
    }

    @Test
    @DisplayName("Get notifications: ResponseEntity.status = 200, OK")
    void getNotificationsReturnsOkAndPaginatedList() throws Exception {
        String message = "Ticket created!";
        UUID ticketId = createTestNotification(message);

        MockHttpServletResponse response = mockMvc.perform(
                get("/notifications")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains(ticketId.toString());
        assertThat(response.getContentAsString()).contains(message);
        assertThat(response.getContentAsString()).contains("content");
        assertThat(response.getContentAsString()).contains("pageable");
    }

    @Test
    @DisplayName("Get notifications with custom pageable: ResponseEntity.status = 200, OK")
    void getNotificationsWithCustomPageableReturnsOk() throws Exception {
        createTestNotification("Ticket created!");

        MockHttpServletResponse response = mockMvc.perform(
                get("/notifications?page=0&size=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("\"size\":5");
    }


    @Test
    @DisplayName("Get notification by id when notification exists: ResponseEntity.status = 200, OK")
    void getNotificationByIdWhenNotificationExistsReturnsOk() throws Exception {
        String message = "Technician assigned!";
        UUID ticketId = createTestNotification(message);
        UUID notificationId = getNotificationIdByTicketId(ticketId);

        MockHttpServletResponse response = mockMvc.perform(
                get("/notifications/{id}", notificationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains(notificationId.toString());
        assertThat(response.getContentAsString()).contains(ticketId.toString());
        assertThat(response.getContentAsString()).contains(message);
    }

    @Test
    @DisplayName("Get notification by id when notification does not exist: ResponseEntity.status = 400, Bad Request")
    void getNotificationByIdWhenNotificationDoesNotExistReturnsBadRequest() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        MockHttpServletResponse response = mockMvc.perform(
                get("/notifications/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        ExceptionMessage body = exceptionMessageJson.parseObject(response.getContentAsString());
        assertThat(body.message()).isEqualTo("Notification with id " + nonExistentId + " not found");
    }

    @Test
    @DisplayName("Get notifications by ticket id when notifications exist: ResponseEntity.status = 200, OK")
    void getNotificationsByTicketIdReturnsOk() throws Exception {
        String message = "Ticket status changed to IN_PROGRESS";
        UUID ticketId = createTestNotification(message);

        MockHttpServletResponse response = mockMvc.perform(
                get("/notifications/ticket/{ticketId}", ticketId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains(ticketId.toString());
        assertThat(response.getContentAsString()).contains(message);
    }

    @Test
    @DisplayName("Get notifications by ticket id when there are none: ResponseEntity.status = 200, OK with empty content")
    void getNotificationsByTicketIdWhenNoneExistReturnsOkWithEmptyContent() throws Exception {
        UUID ticketIdWithNoNotifications = UUID.randomUUID();

        MockHttpServletResponse response = mockMvc.perform(
                get("/notifications/ticket/{ticketId}", ticketIdWithNoNotifications)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("\"content\":[]");
    }

    @Test
    @DisplayName("Get notifications by ticket id only returns notifications for that ticket")
    void getNotificationsByTicketIdOnlyReturnsMatchingNotifications() throws Exception {
        UUID targetTicketId = createTestNotification("Notification for target ticket");
        UUID otherTicketId = createTestNotification("Notification for other ticket");

        MockHttpServletResponse response = mockMvc.perform(
                get("/notifications/ticket/{ticketId}", targetTicketId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains(targetTicketId.toString());
        assertThat(response.getContentAsString()).doesNotContain(otherTicketId.toString());
    }
}