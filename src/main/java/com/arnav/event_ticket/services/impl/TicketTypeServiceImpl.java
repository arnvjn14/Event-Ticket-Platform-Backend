package com.arnav.event_ticket.services.impl;

import com.arnav.event_ticket.domain.entities.Ticket;
import com.arnav.event_ticket.domain.entities.TicketStatusEnum;
import com.arnav.event_ticket.domain.entities.TicketType;
import com.arnav.event_ticket.domain.entities.User;
import com.arnav.event_ticket.exceptions.TicketTypeNotFoundException;
import com.arnav.event_ticket.exceptions.TicketsSoldOutException;
import com.arnav.event_ticket.exceptions.UserNotFoundException;
import com.arnav.event_ticket.repositories.TicketRepository;
import com.arnav.event_ticket.repositories.TicketTypeRepository;
import com.arnav.event_ticket.repositories.UserRepository;
import com.arnav.event_ticket.services.QrCodeService;
import com.arnav.event_ticket.services.TicketTypeService;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketRepository ticketRepository;
    private final QrCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                String.format("User with ID %s was not found", userId)
        ));

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId)
                .orElseThrow(() -> new TicketTypeNotFoundException(
                        String.format("Ticket type with ID %s was not found", ticketTypeId)
                ));

        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());
        Integer totalAvailable = ticketType.getTotalAvailable();

        if(purchasedTickets + 1 > totalAvailable) {
            throw new TicketsSoldOutException();
        }

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket savedTicket = ticketRepository.save(ticket);
        qrCodeService.generateQrCode(savedTicket);

        return ticketRepository.save(savedTicket);
    }
}
