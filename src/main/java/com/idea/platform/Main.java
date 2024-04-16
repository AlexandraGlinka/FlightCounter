package com.idea.platform;

import com.idea.platform.Entity.Ticket;
import com.idea.platform.Service.JsonSimpleParse;
import com.idea.platform.Service.JsonSimpleParserImpl;
import com.idea.platform.Service.TicketFilter;
import com.idea.platform.Service.TicketFilterImpl;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        JsonSimpleParse parser = new JsonSimpleParserImpl();
        List<Ticket> tickets = parser.parse();

        TicketFilter ticketFilter = new TicketFilterImpl();
        Map<String, Long> carrierAndMinTime = ticketFilter.calcMinTimeBtwVvoAndTlv(tickets);
        System.out.println("Минимальное время полета между городами Владивосток и Тель-Авив для каждого авиаперевозчика (в минутах)");
        for (Map.Entry<String, Long> entry : carrierAndMinTime.entrySet()) {
            System.out.println(entry);
        }

        System.out.println();

        long diffAvgAndMedPrice = ticketFilter.calcDiffBtwAverageAndMedianPriceBtwVvoAndTlv(tickets);
        System.out.println("Разницу между средней ценой  и медианой для полета между городами  Владивосток и Тель-Авив");
        System.out.println(diffAvgAndMedPrice);
    }
}