package com.idea.platform.Entity;

import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class Ticket {
    String origin;
    String originName;
    String destination;
    String destinationName;
    Date departureDate;
    Time departureTime;
    Date arrivalDate;
    Time arrivalTime;
    String carrier;
    Integer stops;
    Integer price;
}
