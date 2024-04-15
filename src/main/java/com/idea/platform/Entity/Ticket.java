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
    String departureDate;
    String departureTime;
    String arrivalDate;
    String arrivalTime;
    String carrier;
    long stops;
    long price;
}
