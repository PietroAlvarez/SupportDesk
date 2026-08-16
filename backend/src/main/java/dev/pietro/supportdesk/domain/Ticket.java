package dev.pietro.supportdesk.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String requester;
    private String location;
    private String description;

    @Enumerated(EnumType.STRING)
    private TicketCategory category;

    @Enumerated(EnumType.STRING)
    private TicketPriority priority;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected Ticket() {
    }

    public Ticket(String title, String requester, String location, String description,
                  TicketCategory category, TicketPriority priority) {
        this.title = title;
        this.requester = requester;
        this.location = location;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.status = TicketStatus.OPEN;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public void update(String title, String requester, String location, String description,
                       TicketCategory category, TicketPriority priority, TicketStatus status) {
        this.title = title;
        this.requester = requester;
        this.location = location;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public void advance() {
        status = switch (status) {
            case OPEN -> TicketStatus.IN_PROGRESS;
            case IN_PROGRESS, RESOLVED -> TicketStatus.RESOLVED;
        };
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getRequester() { return requester; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public TicketCategory getCategory() { return category; }
    public TicketPriority getPriority() { return priority; }
    public TicketStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
