package dev.pietro.supportdesk.service;

import dev.pietro.supportdesk.api.DashboardResponse;
import dev.pietro.supportdesk.api.TicketRequest;
import dev.pietro.supportdesk.api.TicketResponse;
import dev.pietro.supportdesk.domain.Ticket;
import dev.pietro.supportdesk.domain.TicketPriority;
import dev.pietro.supportdesk.domain.TicketStatus;
import dev.pietro.supportdesk.repository.TicketRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TicketService {

    private final TicketRepository repository;

    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<TicketResponse> list(String search, TicketStatus status) {
        String pattern = search == null || search.isBlank() ? null : "%" + search.trim().toLowerCase() + "%";
        return repository.search(pattern, status).stream().map(TicketResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public DashboardResponse dashboard() {
        List<Ticket> tickets = repository.findAll();
        return new DashboardResponse(
                tickets.size(),
                count(tickets, TicketStatus.OPEN),
                count(tickets, TicketStatus.IN_PROGRESS),
                count(tickets, TicketStatus.RESOLVED),
                tickets.stream().filter(ticket -> ticket.getPriority() == TicketPriority.CRITICAL
                        && ticket.getStatus() != TicketStatus.RESOLVED).count());
    }

    public TicketResponse create(TicketRequest request) {
        Ticket ticket = new Ticket(request.title(), request.requester(), request.location(),
                request.description(), request.category(), request.priority());
        return TicketResponse.from(repository.save(ticket));
    }

    public TicketResponse update(long id, TicketRequest request) {
        Ticket ticket = find(id);
        ticket.update(request.title(), request.requester(), request.location(), request.description(),
                request.category(), request.priority(), request.status() == null ? ticket.getStatus() : request.status());
        return TicketResponse.from(ticket);
    }

    public TicketResponse advance(long id) {
        Ticket ticket = find(id);
        ticket.advance();
        return TicketResponse.from(ticket);
    }

    public void delete(long id) {
        repository.delete(find(id));
    }

    private Ticket find(long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
    }

    private long count(List<Ticket> tickets, TicketStatus status) {
        return tickets.stream().filter(ticket -> ticket.getStatus() == status).count();
    }
}
