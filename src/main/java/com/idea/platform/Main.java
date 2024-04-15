package com.idea.platform;

import com.idea.platform.Entity.Ticket;
import com.idea.platform.Service.JsonSimpleParse;
import com.idea.platform.Service.JsonSimpleParserImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

//        Object o = new JSONParser().parse(new FileReader(tickets.json));

//        String ticketsJson = tickets.json;
//        Ticket ticket = new Gson().fromJson(jsonInput, Ticket.class);

        JsonSimpleParse parser = new JsonSimpleParserImpl();
        List<Ticket> tickets = parser.parse();

        for(Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }
}