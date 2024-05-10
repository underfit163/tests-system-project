package com.underfit.testsystembackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id", nullable = false)
    private Long id;

    @Column(name = "color", nullable = false, length = Integer.MAX_VALUE)
    private String color;

    @Column(name = "date_withdrawal")
    private LocalDate dateWithdrawal;

    @Column(name = "reason_withdrawal", length = Integer.MAX_VALUE)
    private String reasonWithdrawal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}