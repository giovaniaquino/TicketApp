package com.ticketapp.api.models;

import com.ticketapp.api.enums.UserEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter @Setter
@Table(name = "tb_user")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserEnum.UserType type;

    @OneToMany(mappedBy = "client")
    private Set<TicketModel> clientTickets = new HashSet<>();

    @OneToMany(mappedBy = "technician")
    private Set<TicketModel> techTickets = new HashSet<>();
}
