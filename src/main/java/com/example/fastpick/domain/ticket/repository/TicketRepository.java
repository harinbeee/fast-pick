package com.example.fastpick.domain.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fastpick.domain.ticket.model.Ticket;

public interface TicketRepository extends JpaRepository <Ticket,Long> {
}
