package com.ticketapp.api.repositories;

import com.ticketapp.api.enums.TicketEnum;
import com.ticketapp.api.models.TicketModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<TicketModel, Long> {

    List<TicketModel> findByClientId(UUID clientId);

    List<TicketModel> findByTechnicianId(UUID technicianId);

    List<TicketModel> findByStatus(TicketEnum.TicketStatus status);
}
