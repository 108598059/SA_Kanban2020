package phd.sa.csie.ntut.edu.tw.model.service.cycletimecalculator.event;

import phd.sa.csie.ntut.edu.tw.model.domain.AbstractDomainEvent;

import java.util.Date;
import java.util.UUID;

public class CycleTimeCalculatedEvent extends AbstractDomainEvent {
    public CycleTimeCalculatedEvent(String cardID, String startColumnID, String endColumnID, Date startTime, Date endTime) {
        super(UUID.randomUUID().toString(), "[Cycle Time Calculated Event] Calculate cycle time of card: " + cardID +
                "from start column: " + startColumnID + " to end column: " + endColumnID + " during the period: " + startTime + "~" + endTime);
    }
}
