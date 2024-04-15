package com.idea.platform.Entity;

import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class Ticket {
    String origin;
    String origin_name;
    String destination;
    String destination_name;
    Date departure_date;
    Time departure_time;
    Date arrival_date;
    Time arrival_time;
    String carrier;
    Integer stops;
    Integer price;
}
