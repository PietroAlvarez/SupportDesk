package dev.pietro.supportdesk.config;

import dev.pietro.supportdesk.domain.Ticket;
import dev.pietro.supportdesk.domain.TicketCategory;
import dev.pietro.supportdesk.domain.TicketPriority;
import dev.pietro.supportdesk.repository.TicketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoDataConfig {

    @Bean
    CommandLineRunner seedTickets(TicketRepository repository) {
        return args -> {
            Ticket network = new Ticket("Wi-Fi inestable en biblioteca", "María López", "Biblioteca",
                    "La conexión se interrumpe durante las clases.", TicketCategory.NETWORK, TicketPriority.HIGH);
            Ticket account = new Ticket("Restablecer cuenta institucional", "Diego Muñoz", "Administración",
                    "Usuario bloqueado después de varios intentos.", TicketCategory.ACCOUNTS, TicketPriority.MEDIUM);
            account.advance();
            Ticket audio = new Ticket("Proyector sin señal", "Carolina Vega", "Sala 3",
                    "El equipo enciende pero no recibe señal HDMI.", TicketCategory.AUDIO_VISUAL, TicketPriority.CRITICAL);
            Ticket software = new Ticket("Actualizar suite de oficina", "Javier Soto", "Laboratorio",
                    "Se requiere la versión más reciente en doce equipos.", TicketCategory.SOFTWARE, TicketPriority.LOW);
            software.advance();
            software.advance();
            repository.saveAll(java.util.List.of(network, account, audio, software));
        };
    }
}
