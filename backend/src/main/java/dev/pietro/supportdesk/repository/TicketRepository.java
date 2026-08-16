package dev.pietro.supportdesk.repository;

import dev.pietro.supportdesk.domain.Ticket;
import dev.pietro.supportdesk.domain.TicketStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("""
            select t from Ticket t
            where (:status is null or t.status = :status)
              and (:search is null or lower(t.title) like :search
                   or lower(t.requester) like :search
                   or lower(t.location) like :search)
            order by t.createdAt desc
            """)
    List<Ticket> search(@Param("search") String search, @Param("status") TicketStatus status);
}
