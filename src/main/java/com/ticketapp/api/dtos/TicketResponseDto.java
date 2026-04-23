package com.ticketapp.api.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class TicketResponseDto {

    private Long id;
    private String title;
    private String description;
    private String status;
    private UUID clientId;
    private UUID technicianId;
}