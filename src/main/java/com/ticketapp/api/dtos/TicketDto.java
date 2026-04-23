package com.ticketapp.api.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class TicketDto {
    private String title;
    private String description;
    private UUID clientId;
    private UUID technicianId;
}
