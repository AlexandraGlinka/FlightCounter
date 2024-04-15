package com.idea.platform.Entity;

import lombok.Data;

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
    String carrier; //авиаперевозчик
    long stops;
    long price;
}
