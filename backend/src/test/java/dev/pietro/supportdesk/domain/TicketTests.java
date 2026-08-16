package dev.pietro.supportdesk.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TicketTests {

    @Test
    void advancesThroughTheSupportWorkflow() {
        Ticket ticket = new Ticket("Impresora", "Ana", "Oficina", "Sin conexión",
                TicketCategory.HARDWARE, TicketPriority.HIGH);

        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.OPEN);
        ticket.advance();
        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.IN_PROGRESS);
        ticket.advance();
        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.RESOLVED);
    }
}
