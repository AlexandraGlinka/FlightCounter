package com.idea.platform.Service;

import com.idea.platform.Entity.Ticket;

import java.util.List;
import java.util.Map;

public interface TicketFilter {
    public Map<String, Long> calcMinTimeBtwVvoAndTlv(List<Ticket> tickets);

    public long calcDiffBtwAverageAndMedianPriceBtwVvoAndTlv(List<Ticket> tickets);

}
