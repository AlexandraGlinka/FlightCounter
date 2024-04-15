package com.idea.platform.Service;

import com.idea.platform.Entity.Ticket;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TicketFilterImpl implements TicketFilter {
    @Override
    public Integer calcMinTimeBtwVvoAndTlv(List<Ticket> tickets) {
        List<Ticket> ticketsBtwVvoAndTlv = chooseTicketsBtwVvoAndTlv(tickets); // все перевозки из Владивостока в Тель-Авив
        Map<String, List<Ticket>> groupedByCarrier = groupTicketsByCarrier(ticketsBtwVvoAndTlv); // сгруппировали по направлениями
        Map<String, Duration> ticketsAndTime = new HashMap<>(); // мапа для направлений и минимального времени полета

        for (String carrier : groupedByCarrier.keySet()) {
            List<Ticket> groupedTicket = groupedByCarrier.get(carrier);
            Duration minFlightDuration = calculateMinFlightTime(groupedTicket);
            for (Ticket ticket : groupedTicket) {
                ticketsAndTime.put(ticket.toString(), calculateFlightTime((Ticket) ticket));
            }

        }

//        Map<String, Duration> ticketsAndTime = new HashMap<>();

//        for (Ticket ticket : ticketsBtwVvoAndTlv) {
//            ticketsAndTime.put(ticket.toString(), calculateFlightTime(ticket));
//        }

        return null;
    }


    @Override
    public Integer calcDiffBtwAverageAndMedianPriceBtwVvoAndTlv(List<Ticket> tickets) {
        return null;
    }

    private List<Ticket> chooseTicketsBtwVvoAndTlv(List<Ticket> tickets) {
        List<Ticket> ticketsBtwVvoAndTlv = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getOriginName().equals("Владивосток") && ticket.getDestinationName().equals("Тель-Авив")) {
                ticketsBtwVvoAndTlv.add(ticket);
            }
        }
        return ticketsBtwVvoAndTlv;
    }

    private Set<String> chooseUniqueCarriers(List<Ticket> tickets) {
        Set<String> uniqueCarriers = new HashSet<>();
        for (Ticket ticket : tickets) {
            String carrier = ticket.getCarrier();
            uniqueCarriers.add(carrier);
        }
        return uniqueCarriers;
    }

    private Map<String, List<Ticket>> groupTicketsByCarrier(List<Ticket> tickets) {

        Map<String, List<Ticket>> ticketsByCarrier = new HashMap<>();

        for (Ticket ticket : tickets) {
            String carrier = ticket.getCarrier();
            if (!ticketsByCarrier.containsKey(carrier)) {
                ticketsByCarrier.put(carrier, new ArrayList<>());
            }
            ticketsByCarrier.get(carrier).add(ticket);
        }
        return ticketsByCarrier;
    }
    private Duration calculateMinFlightTime(List<Ticket> groupedTicket) {
        return null;
    }

    private Duration calculateFlightTime(Ticket ticket) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

        String departureDateTimeString = ticket.getDepartureDate() + "T" + ticket.getDepartureTime();
        LocalDateTime departureDateTime = LocalDateTime.parse(departureDateTimeString, formatter);

        String arrivalDateTimeString = ticket.getArrivalDate() + "T" + ticket.getArrivalTime();
        LocalDateTime arrivalDateTime = LocalDateTime.parse(arrivalDateTimeString, formatter);

        Duration difference = Duration.between(departureDateTime, arrivalDateTime);

        return difference;
    }
}
