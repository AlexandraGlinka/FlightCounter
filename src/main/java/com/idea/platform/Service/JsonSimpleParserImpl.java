package com.idea.platform.Service;

import com.idea.platform.Entity.Ticket;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonSimpleParserImpl implements JsonSimpleParse {
    public List<Ticket> parse() {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("tickets.json")) {
            JSONObject tisketJsonObject = (JSONObject) parser.parse(reader);
            JSONArray ticketsArray = (JSONArray) tisketJsonObject.get("tickets");

            List<Ticket> tickets = new ArrayList<>();

            for (Object ticketObj : ticketsArray) {

                JSONObject ticketJson = (JSONObject) ticketObj;
                Ticket ticket = new Ticket();
                ticket.setOrigin((String) ticketJson.get("origin"));
                ticket.setOriginName((String) ticketJson.get("origin_name"));
                ticket.setDestination((String) ticketJson.get("destination"));
                ticket.setDestinationName((String) ticketJson.get("destination_name"));
                ticket.setDepartureDate((Date) ticketJson.get("departure_date"));
                ticket.setDepartureTime((Time) ticketJson.get("departure_time"));
                ticket.setArrivalDate((Date) ticketJson.get("arrival_date"));
                ticket.setArrivalTime((Time) ticketJson.get("arrival_time"));
                ticket.setCarrier((String) ticketJson.get("carrier"));
                ticket.setStops((Integer) ticketJson.get("stops"));
                ticket.setPrice((Integer) ticketJson.get("price"));

                tickets.add(ticket);
            }
            return tickets;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
