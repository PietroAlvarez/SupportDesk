package dev.pietro.supportdesk.api;

import dev.pietro.supportdesk.domain.Ticket;
import dev.pietro.supportdesk.domain.TicketCategory;
import dev.pietro.supportdesk.domain.TicketPriority;
import dev.pietro.supportdesk.domain.TicketStatus;
import java.time.LocalDateTime;

public record TicketResponse(
        Long id,
        String title,
        String requester,
        String location,
        String description,
        TicketCategory category,
        TicketPriority priority,
        TicketStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static TicketResponse from(Ticket ticket) {
        return new TicketResponse(ticket.getId(), ticket.getTitle(), ticket.getRequester(),
                ticket.getLocation(), ticket.getDescription(), ticket.getCategory(),
                ticket.getPriority(), ticket.getStatus(), ticket.getCreatedAt(), ticket.getUpdatedAt());
    }
}
