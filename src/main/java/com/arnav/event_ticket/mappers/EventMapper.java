package com.arnav.event_ticket.mappers;

import com.arnav.event_ticket.domain.CreateEventRequest;
import com.arnav.event_ticket.domain.CreateTicketTypeRequest;
import com.arnav.event_ticket.domain.UpdateEventRequest;
import com.arnav.event_ticket.domain.UpdateTicketTypeRequest;
import com.arnav.event_ticket.domain.dtos.*;
import com.arnav.event_ticket.domain.entities.Event;
import com.arnav.event_ticket.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);

    ListEventTicketTypeResponseDto toDto(TicketType ticketType);

    ListEventResponseDto toListEventResponseDto(Event event);

    GetEventDetailsTicketTypesResponseDto toGetEventDetailsTicketTypesResponseDto(
            TicketType ticketType);

    GetEventDetailsResponseDto toGetEventDetailsResponseDto(Event event);

    UpdateTicketTypeRequest fromDto(UpdateTicketTypeRequestDto dto);

    UpdateEventRequest fromDto(UpdateEventRequestDto dto);

    UpdateTicketTypeResponseDto toUpdateTicketTypeResponseDto(TicketType ticketType);

    UpdateEventResponseDto toUpdateEventResponseDto(Event event);

    ListPublishedEventResponseDto toListPublishedEventResponseDto(Event event);

//    GetPublishedEventDetailsTicketTypesResponseDto toGetPublishedEventDetailsTicketTypesResponseDto(
//            TicketType ticketType);
//
//    GetPublishedEventDetailsResponseDto toGetPublishedEventDetailsResponseDto(Event event);
}
