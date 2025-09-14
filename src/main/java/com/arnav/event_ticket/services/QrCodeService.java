package com.arnav.event_ticket.services;

import com.arnav.event_ticket.domain.entities.QrCode;
import com.arnav.event_ticket.domain.entities.Ticket;

import java.util.UUID;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);

    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}