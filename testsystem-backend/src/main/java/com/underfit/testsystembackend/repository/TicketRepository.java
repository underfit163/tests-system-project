package com.underfit.testsystembackend.repository;

import com.underfit.testsystembackend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}