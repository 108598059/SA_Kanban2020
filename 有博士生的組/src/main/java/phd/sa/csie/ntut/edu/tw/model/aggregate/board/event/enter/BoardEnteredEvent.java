package phd.sa.csie.ntut.edu.tw.model.aggregate.board.event.enter;

import phd.sa.csie.ntut.edu.tw.model.domain.AbstractDomainEvent;

public class BoardEnteredEvent extends AbstractDomainEvent {

    public BoardEnteredEvent(String sourceID) {
        super(sourceID, "[Board Entered Event] User entered board: " + sourceID);
    }
}
