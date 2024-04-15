package com.idea.platform;

import com.idea.platform.Entity.Ticket;
import com.idea.platform.Service.JsonSimpleParse;
import com.idea.platform.Service.JsonSimpleParserImpl;
import com.idea.platform.Service.TicketFilter;
import com.idea.platform.Service.TicketFilterImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        JsonSimpleParse parser = new JsonSimpleParserImpl();
        List<Ticket> tickets = parser.parse();

        for(Ticket ticket : tickets) {
            System.out.println(ticket);
        }

//        TicketFilter ticketFilter = new TicketFilterImpl();
//        ticketFilter.calcMinTimeBtwVvoAndTlv(tickets).forEach(System.out::println);
    }
}