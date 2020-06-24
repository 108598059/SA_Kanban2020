package domain.model.service.cycleTime;

import domain.model.FlowEvent;
import domain.model.aggregate.workflow.event.CardCommitted;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class CycleTimeCalculatorTest {
    private CycleTimeCalculator cycleTimeCalculator;
    private List<String> stageIds;
    private List<FlowEvent> flowEvents;
    private String cardId;
    @Before
    public void SetUp(){
        stageIds = new ArrayList<>();
        flowEvents = new ArrayList<>();
        stageIds.add("stageId");
        cardId = "cardId";
        flowEvents.add(new CardCommitted("workflowId", "laneId", cardId));
        cycleTimeCalculator = new CycleTimeCalculator(stageIds, flowEvents, cardId);
    }

    @Test
    public void Calculate_CycleTime_should_generate_CycleTimeCalculated_event_in_the_domainEvent_list() {
        cycleTimeCalculator.process();
        assertThat(cycleTimeCalculator.getDomainEvents().size()).isEqualTo(1);
        assertThat(cycleTimeCalculator.getDomainEvents().get(0).detail()).startsWith("CycleTimeCalculated");

        cycleTimeCalculator.clearDomainEvents();
        assertThat(cycleTimeCalculator.getDomainEvents().size()).isEqualTo(0);
    }
}
