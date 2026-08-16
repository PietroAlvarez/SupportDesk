package dev.pietro.supportdesk.controller;

import dev.pietro.supportdesk.api.TicketRequest;
import dev.pietro.supportdesk.api.TicketResponse;
import dev.pietro.supportdesk.domain.TicketStatus;
import dev.pietro.supportdesk.service.TicketService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @GetMapping
    public List<TicketResponse> list(@RequestParam(required = false) String search,
                                     @RequestParam(required = false) TicketStatus status) {
        return service.list(search, status);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponse create(@Valid @RequestBody TicketRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public TicketResponse update(@PathVariable long id, @Valid @RequestBody TicketRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/advance")
    public TicketResponse advance(@PathVariable long id) {
        return service.advance(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
