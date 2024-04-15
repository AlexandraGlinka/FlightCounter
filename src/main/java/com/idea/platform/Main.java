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

        for(Ticket ticket : tickets) {
            System.out.println(ticket);
        }

        System.out.println();

        TicketFilter ticketFilter = new TicketFilterImpl();
        Map<String, Long> carrierAndMinTime = ticketFilter.calcMinTimeBtwVvoAndTlv(tickets);
        for (Map.Entry<String, Long> entry : carrierAndMinTime.entrySet()) {
            System.out.println(entry);
        }
    }
}