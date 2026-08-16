package dev.pietro.supportdesk.controller;

import dev.pietro.supportdesk.api.DashboardResponse;
import dev.pietro.supportdesk.service.TicketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final TicketService service;

    public DashboardController(TicketService service) {
        this.service = service;
    }

    @GetMapping
    public DashboardResponse get() {
        return service.dashboard();
    }
}
