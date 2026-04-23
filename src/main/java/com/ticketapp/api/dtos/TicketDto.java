package com.ticketapp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class TicketDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private UUID clientId;

    private UUID technicianId;
}
