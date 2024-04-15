package com.idea.platform.Service;

import com.idea.platform.Entity.Ticket;

import java.util.List;

public interface TicketFilter {
    // Минимальное время полета между городами Владивосток и Тель-Авив для каждого авиаперевозчика
    public Integer calcMinTimeBtwVvoAndTlv(List<Ticket> tickets);


    // Разницу между средней ценой  и медианой для полета между городами  Владивосток и Тель-Авив
    public Integer calcDiffBtwAverageAndMedianPriceBtwVvoAndTlv(List<Ticket> tickets);

}
