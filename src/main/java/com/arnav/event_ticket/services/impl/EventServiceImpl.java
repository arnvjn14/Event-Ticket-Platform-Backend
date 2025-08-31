package com.arnav.event_ticket.services.impl;

import com.arnav.event_ticket.domain.CreateEventRequest;
import com.arnav.event_ticket.domain.entities.Event;
import com.arnav.event_ticket.domain.entities.TicketType;
import com.arnav.event_ticket.domain.entities.User;
import com.arnav.event_ticket.exceptions.UserNotFoundException;
import com.arnav.event_ticket.repositories.EventRepository;
import com.arnav.event_ticket.repositories.UserRepository;
import com.arnav.event_ticket.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public Event createEvent(UUID organizerId, CreateEventRequest event) {
        User organizer=userRepository.findById(organizerId)
                .orElseThrow(()->
                    new UserNotFoundException(
                            String.format("User with Id '%s' not found",organizerId )
                    )
                );
        Event eventToCreate= new Event();

        List<TicketType> ticketTypesToCreate = event.getTicketTypes().stream().map(
                ticketType -> {
                    TicketType ticketTypeToCreate = new TicketType();
                    ticketTypeToCreate.setName(ticketType.getName());
                    ticketTypeToCreate.setPrice(ticketType.getPrice());
                    ticketTypeToCreate.setDescription(ticketType.getDescription());
                    ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                    ticketTypeToCreate.setEvent(eventToCreate);
                    return ticketTypeToCreate;
                }).toList();

        eventToCreate.setName(event.getName());
        eventToCreate.setStart(event.getStart());
        eventToCreate.setEnd(event.getEnd());
        eventToCreate.setVenue(event.getVenue());
        eventToCreate.setSalesStart(event.getSalesStart());
        eventToCreate.setSalesEnd(event.getSalesEnd());
        eventToCreate.setStatus(event.getStatus());
        eventToCreate.setOrganizer(organizer);
        eventToCreate.setTicketTypes(ticketTypesToCreate);

        return eventRepository.save(eventToCreate);


    }

    @Override
    public Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Event> getEventForOrganizer(UUID organizerId, UUID id) {
        return Optional.empty();
    }

//    @Override
//    public Event updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequest event) {
//        return null;
//    }

    @Override
    public void deleteEventForOrganizer(UUID organizerId, UUID id) {

    }

    @Override
    public Page<Event> listPublishedEvents(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Event> searchPublishedEvents(String query, Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Event> getPublishedEvent(UUID id) {
        return Optional.empty();
    }
}
