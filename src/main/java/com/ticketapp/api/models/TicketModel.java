package com.ticketapp.api.models;

import com.ticketapp.api.enums.TicketEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@Table(name = "tb_ticket")
public class TicketModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @CreatedDate
    @Column()
    private LocalDateTime dateOpen;

    @LastModifiedDate
    @Column
    private LocalDateTime dateClose;

    @Enumerated(EnumType.STRING)
    @Column()
    private TicketEnum.TicketStatus status;

    @Enumerated(EnumType.STRING)
    @Column()
    private TicketEnum.TicketPrio priority;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private UserModel client;

    @ManyToOne
    @JoinColumn(name = "technician_id")
    private UserModel technician;
}
