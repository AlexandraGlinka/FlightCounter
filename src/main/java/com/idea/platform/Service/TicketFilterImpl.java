package com.idea.platform.Service;

import com.idea.platform.Entity.Ticket;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

public class TicketFilterImpl implements TicketFilter {
    /**
     * @param tickets список перелетов
     * @return Map<String, Long>
     * возвращает минимальное время перелета с Владивостока до Тель-Авив
     * для каждого авиаперевозчика
     */
    @Override
    public Map<String, Long> calcMinTimeBtwVvoAndTlv(List<Ticket> tickets) {
        List<Ticket> ticketsBtwVvoAndTlv = chooseTicketsBtwVvoAndTlv(tickets); // все перевозки из Владивостока в Тель-Авив
        Map<String, List<Ticket>> groupedByCarrier = groupTicketsByCarrier(ticketsBtwVvoAndTlv); // сгруппировали по направлениями
        Map<String, Long> ticketsAndTime = new HashMap<>(); // мапа для направлений и минимального времени полета

        for (String carrier : groupedByCarrier.keySet()) {
            List<Ticket> groupedTicket = groupedByCarrier.get(carrier);
            Long minFlightDuration = calculateMinFlightTime(groupedTicket);

            ticketsAndTime.put(carrier, minFlightDuration);
        }

        return ticketsAndTime;
    }

    /**
     *
     * @param tickets
     * @return
     */
    @Override
    public Integer calcDiffBtwAverageAndMedianPriceBtwVvoAndTlv(List<Ticket> tickets) {
        return null;
    }

    /**
     *
     * @param tickets список всех перелетов
     * @return List<Ticket>
     * возвращает список перелетов только из Владивостока в Тель-Авив
     */
    private List<Ticket> chooseTicketsBtwVvoAndTlv(List<Ticket> tickets) {
        List<Ticket> ticketsBtwVvoAndTlv = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getOriginName().equals("Владивосток") && ticket.getDestinationName().equals("Тель-Авив")) {
                ticketsBtwVvoAndTlv.add(ticket);
            }
        }
        return ticketsBtwVvoAndTlv;
    }

    /**
     *
     * @param tickets список перелетов только из Владивостока в Тель-Авив
     * @return Map<String, List<Ticket>>
     * возвращает мапу, где ключ - авиаперевозчик, значение - список перелетов у этого перевозчика
     */
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

    /**
     *
     * @param groupedTicket сгруппированный список перелетов у каждого перевозчика
     * @return Long
     * возвращает минимальное время перелета у каждого перевозчика
     */
    private Long calculateMinFlightTime(List<Ticket> groupedTicket) {
        Long minFlightTime = Long.MAX_VALUE;
        for (Ticket ticket : groupedTicket) {
            Long flightTime = calculateFlightTime(ticket);
            if (flightTime < minFlightTime) {
                minFlightTime = flightTime;
            }
        }
        return minFlightTime;
    }

    /**
     *
     * @param ticket перелет
     * @return Long
     * возвращает время перелета
     */
    private Long calculateFlightTime(Ticket ticket) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("H:mm"))
                .appendOptional(DateTimeFormatter.ofPattern("HH:mm"))
                .toFormatter();

        LocalDate departureDate = LocalDate.parse(ticket.getDepartureDate(), dateFormatter);
        LocalTime departureTime = LocalTime.parse(ticket.getDepartureTime(), timeFormatter);
        LocalDateTime departureDateTime = LocalDateTime.of(departureDate, departureTime);

        LocalDate arrivalDate = LocalDate.parse(ticket.getArrivalDate(), dateFormatter);
        LocalTime arrivalTime = LocalTime.parse(ticket.getArrivalTime(), timeFormatter);
        LocalDateTime arrivalDateTime = LocalDateTime.of(arrivalDate, arrivalTime);

        Duration difference = Duration.between(departureDateTime, arrivalDateTime);
        long minutes = difference.toMinutes();

        return minutes;
    }
}
