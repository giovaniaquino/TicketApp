package com.ticketapp.api.services;

import com.ticketapp.api.enums.TicketEnum;
import com.ticketapp.api.enums.UserEnum;
import com.ticketapp.api.models.TicketModel;
import com.ticketapp.api.models.UserModel;
import com.ticketapp.api.repositories.TicketRepository;
import com.ticketapp.api.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public TicketModel createTicket(TicketModel ticketModel){
        TicketModel ticket = new TicketModel();

        ticket.setTitle(ticketModel.getTitle());
        ticket.setDescription(ticketModel.getDescription());
        ticket.setStatus(TicketEnum.TicketStatus.OPEN);
        ticket.setClient(validateClient(ticketModel.getClient()));

        if (ticketModel.getTechnician() != null) {
            ticket.setTechnician(validateTechnician(ticketModel.getTechnician()));
        }
        ticket.setDateOpen(ticketModel.getDateOpen());

        return ticketRepository.save(ticket);
    }

    private UserModel validateClient(UserModel clientInput){
        if (clientInput == null || clientInput.getId() == null){
            throw new IllegalArgumentException("Client cannot be null.");
        }

        UserModel client = userRepository.findById(clientInput.getId())
                .orElseThrow(() -> new EntityNotFoundException("Client does not exist."));

        if (client.getType() == UserEnum.UserType.TECHNICIAN){
            throw new IllegalArgumentException("Technician canot open tickets.");
        }

        return client;
    }

    private UserModel validateTechnician(UserModel technicianInput){
        if (technicianInput.getId() == null) {
            throw new IllegalArgumentException("Technician ID cannot be null.");
        }

        UserModel technician = userRepository.findById(technicianInput.getId())
                .orElseThrow(() -> new EntityNotFoundException("Technician does not exist."));

        if (technician.getType() != UserEnum.UserType.TECHNICIAN) {
            throw new IllegalArgumentException("Clients cannot be set as technician.");
        }

        return technician;
    }
}
