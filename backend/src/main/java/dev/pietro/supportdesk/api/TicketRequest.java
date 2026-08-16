package dev.pietro.supportdesk.api;

import dev.pietro.supportdesk.domain.TicketCategory;
import dev.pietro.supportdesk.domain.TicketPriority;
import dev.pietro.supportdesk.domain.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TicketRequest(
        @NotBlank @Size(max = 120) String title,
        @NotBlank @Size(max = 80) String requester,
        @NotBlank @Size(max = 80) String location,
        @Size(max = 500) String description,
        @NotNull TicketCategory category,
        @NotNull TicketPriority priority,
        TicketStatus status
) {
}
