package com.ticketapp.api.services;

import com.ticketapp.api.dtos.TicketDto;
import com.ticketapp.api.dtos.TicketResponseDto;
import com.ticketapp.api.enums.TicketEnum;
import com.ticketapp.api.enums.UserEnum;
import com.ticketapp.api.models.TicketModel;
import com.ticketapp.api.models.UserModel;
import com.ticketapp.api.repositories.TicketRepository;
import com.ticketapp.api.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public TicketResponseDto createTicket(TicketDto ticketDto){
        TicketModel ticket = new TicketModel();

        ticket.setTitle(ticketDto.getTitle());
        ticket.setDescription(ticketDto.getDescription());
        ticket.setStatus(TicketEnum.TicketStatus.OPEN);
        ticket.setClient(validateClient(ticketDto.getClientId()));

        if (ticketDto.getTechnicianId() != null) {
            ticket.setTechnician(validateTechnician(ticketDto.getTechnicianId()));
        }

        TicketModel savedTicket = ticketRepository.save(ticket);

        TicketResponseDto responseDto = new TicketResponseDto();
        responseDto.setId(savedTicket.getId());
        responseDto.setTitle(savedTicket.getTitle());
        responseDto.setDescription(savedTicket.getDescription());
        responseDto.setStatus(String.valueOf(savedTicket.getStatus()));
        responseDto.setClientId(savedTicket.getClient().getId());

        if (savedTicket.getTechnician().getId() != null) {
            responseDto.setTechnicianId(savedTicket.getTechnician().getId());
        }

        return responseDto;
    }

    private UserModel validateClient(UUID clientId){
        if (clientId == null){
            throw new IllegalArgumentException("Client cannot be null.");
        }

        UserModel client = userRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client does not exist."));

        if (client.getType() != UserEnum.UserType.CLIENT){
            throw new IllegalArgumentException("Only clients can open tickets.");
        }

        return client;
    }

    private UserModel validateTechnician(UUID technicianId){
        if (technicianId == null) {
            throw new IllegalArgumentException("Technician ID cannot be null.");
        }

        UserModel technician = userRepository.findById(technicianId)
                .orElseThrow(() -> new EntityNotFoundException("Technician does not exist."));

        if (technician.getType() != UserEnum.UserType.TECHNICIAN) {
            throw new IllegalArgumentException("Clients cannot be set as technician.");
        }

        return technician;
    }
}
