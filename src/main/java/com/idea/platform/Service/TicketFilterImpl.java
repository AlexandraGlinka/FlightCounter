package com.idea.platform.Service;

import com.idea.platform.Entity.Ticket;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class TicketFilterImpl implements TicketFilter {
    /**
     * @param tickets список перелетов
     * @return Map<String, Long>
     * возвращает минимальное время перелета с Владивостока до Тель-Авив
     * для каждого авиаперевозчика
     */
    @Override
    public Map<String, Long> calcMinTimeBtwVvoAndTlv(List<Ticket> tickets) {

        List<Ticket> ticketsBtwVvoAndTlv = chooseTicketsBtwVvoAndTlv(tickets);
        Map<String, List<Ticket>> groupedByCarrier = ticketsBtwVvoAndTlv.stream()
                .collect(Collectors.groupingBy(ticket -> ticket.getCarrier()));
        Map<String, Long> ticketsAndTime = new HashMap<>();

        for (String carrier : groupedByCarrier.keySet()) {
            List<Ticket> groupedTicket = groupedByCarrier.get(carrier);
            Long minFlightDuration = calculateMinFlightTime(groupedTicket);

            ticketsAndTime.put(carrier, minFlightDuration);
        }

        return ticketsAndTime;
    }

    /**
     * @param tickets
     * @return возвращает разницу между средней ценой  и медианой для полета между городами Владивосток и Тель-Авив
     */
    @Override
    public long calcDiffBtwAverageAndMedianPriceBtwVvoAndTlv(List<Ticket> tickets) {

        List<Ticket> ticketsBtwVvoAndTlv = chooseTicketsBtwVvoAndTlv(tickets);
        List<Double> prices = ticketsBtwVvoAndTlv.stream()
                .mapToDouble(Ticket::getPrice)
                .sorted()
                .boxed()
                .collect(Collectors.toList());

        OptionalDouble avgPrice = prices.stream()
                .mapToDouble(Double::doubleValue)
                .average();

        OptionalDouble medianPrice;
        int priceSize = prices.size();
        if (priceSize % 2 == 1) {
            medianPrice = DoubleStream.of(prices.get(priceSize / 2))
                    .average();
        } else {
            medianPrice = DoubleStream.of(prices.get(priceSize / 2), prices.get(priceSize / 2 - 1))
                    .average();
        }

        long diff = (long) avgPrice.getAsDouble() - (long) medianPrice.getAsDouble();

        return diff;
    }

    /**
     * @param tickets список всех перелетов
     * @return List<Ticket>
     * возвращает список перелетов только из Владивостока в Тель-Авив
     */
    private List<Ticket> chooseTicketsBtwVvoAndTlv(List<Ticket> tickets) {
        List<Ticket> ticketsBtwVvoAndTlv = tickets.stream()
                .filter(ticket -> ticket.getOriginName().equals("Владивосток") && ticket.getDestinationName().equals("Тель-Авив"))
                .collect(Collectors.toList());
        return ticketsBtwVvoAndTlv;
    }

    /**
     * @param groupedTicket сгруппированный список перелетов у каждого перевозчика
     * @return Long возвращает минимальное время перелета у каждого перевозчика
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
     * @param ticket перелет
     * @return Long возвращает время перелета
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
