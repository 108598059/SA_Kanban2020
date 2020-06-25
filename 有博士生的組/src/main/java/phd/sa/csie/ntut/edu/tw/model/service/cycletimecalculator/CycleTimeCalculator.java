package phd.sa.csie.ntut.edu.tw.model.service.cycletimecalculator;

import phd.sa.csie.ntut.edu.tw.model.aggregate.board.event.move.CardEnteredColumnEvent;
import phd.sa.csie.ntut.edu.tw.model.aggregate.board.event.move.CardLeftColumnEvent;
import phd.sa.csie.ntut.edu.tw.model.domain.AbstractService;
import phd.sa.csie.ntut.edu.tw.model.service.cycletimecalculator.event.CycleTimeCalculatedEvent;

import java.util.Date;
import java.util.List;

public class CycleTimeCalculator extends AbstractService {
    public CycleTime process(String cardID, String startColumnID, String endColumnID, Date startTime, Date endTime,
                                    List<CardEnteredColumnEvent> enteredEventList, List<CardLeftColumnEvent> leftEventList) {
        Date firstEnteredEventTime = endTime;
        Date lastLeftEventTime = startTime;

        for (CardEnteredColumnEvent enteredEvent: enteredEventList) {
            if (enteredEvent.getToColumnID().equals(startColumnID)) {
                Date timeGot = enteredEvent.occurredOn();

                if (timeGot.before(firstEnteredEventTime)) {
                    firstEnteredEventTime = timeGot;
                }
            }
        }

        for (CardLeftColumnEvent leftEvent: leftEventList) {
            if (leftEvent.getColumnID().equals(endColumnID)) {
                Date timeGot = leftEvent.occurredOn();

                if (timeGot.after(lastLeftEventTime)) {
                    lastLeftEventTime = timeGot;
                }
            }
        }

        this.addDomainEvent(new CycleTimeCalculatedEvent(cardID, startColumnID, endColumnID, startTime, endTime));
        return new CycleTime(cardID, startColumnID, endColumnID, firstEnteredEventTime, lastLeftEventTime);
    }
}
