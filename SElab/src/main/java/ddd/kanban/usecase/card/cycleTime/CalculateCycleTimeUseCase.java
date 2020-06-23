package ddd.kanban.usecase.card.cycleTime;

import ddd.kanban.domain.model.FlowEvent;
import ddd.kanban.domain.model.reporting.CycleTimeCalculator;
import ddd.kanban.usecase.repository.FlowEventRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.kanbanboard.workflow.entity.ColumnEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CalculateCycleTimeUseCase {

    private FlowEventRepository flowEventRepository;
    private WorkflowRepository workflowRepository;

    public CalculateCycleTimeUseCase(WorkflowRepository workflowRepository, FlowEventRepository flowEventRepository) {
        this.workflowRepository = workflowRepository;
        this.flowEventRepository = flowEventRepository;
    }

    public void execute(CalculateCycleTimeInput calculateCycleTimeInput, CalculateCycleTimeOutput calculateCycleTimeOutput){
        List<FlowEventPair> flowEventPairs;
        List<String> laneIntervalIds;
        CycleTimeCalculator cycleTimeCalculator = new CycleTimeCalculator();
        CycleTime cycleTime;

        flowEventPairs = getCardFlowEventPairs(calculateCycleTimeInput.getCardId());
        laneIntervalIds = getLaneIntervalIds(calculateCycleTimeInput.getWorkflowId(), calculateCycleTimeInput.getBeginningLaneId(), calculateCycleTimeInput.getEndLaneId());
        cycleTime = cycleTimeCalculator.calculateCycleTime(flowEventPairs, laneIntervalIds);

        calculateCycleTimeOutput.setCycleTime(cycleTime);
    }


    private List<FlowEventPair> getCardFlowEventPairs(String cardId) {
        List<FlowEvent> cardFlowEvents = this.flowEventRepository.findAll().stream()
                                                                            .filter(flowEvent -> flowEvent.getCardId().equals(cardId))
                                                                            .collect(Collectors.toList());
        List<FlowEventPair> flowEventPairs = new ArrayList<>();
        FlowEvent committed = null;
        boolean isCommitted = true;

        if (cardFlowEvents.size()%2  != 0) cardFlowEvents.add(null);
        for(FlowEvent flowEvent: cardFlowEvents){
            if (isCommitted)
                committed = flowEvent;
            else
                flowEventPairs.add(new FlowEventPair(committed, flowEvent));
            isCommitted = isCommitted == false;
        }
        return flowEventPairs;
    }

    private List<String> getLaneIntervalIds(String workflowId, String beginningLaneId, String endLaneId) {
        List<ColumnEntity> laneEntities = this.workflowRepository.findById(workflowId).getLaneEntities();
        List<String> laneIntervalIds = laneEntities.stream()
                                                    .map(laneEntity -> laneEntity.getId())
                                                    .collect(Collectors.toList());

        return laneIntervalIds;
    }
}
