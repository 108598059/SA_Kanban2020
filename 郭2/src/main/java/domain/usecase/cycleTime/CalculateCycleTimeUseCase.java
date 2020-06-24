package domain.usecase.cycleTime;

import domain.model.DomainEventBus;
import domain.model.aggregate.workflow.Lane;
import domain.model.aggregate.workflow.Workflow;
import domain.model.FlowEvent;
import domain.model.service.cycleTime.CycleTimeCalculator;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.workflow.WorkflowTransfer;
import domain.usecase.workflow.repository.IWorkflowRepository;

import java.util.ArrayList;
import java.util.List;

public class CalculateCycleTimeUseCase {
    private IWorkflowRepository workflowRepository;
    private IFlowEventRepository flowEventRepository;
    private DomainEventBus domainEventBus;

    public CalculateCycleTimeUseCase(IWorkflowRepository workflowRepository, IFlowEventRepository flowEventRepository, DomainEventBus domainEventBus) {
        this.flowEventRepository = flowEventRepository;
        this.workflowRepository = workflowRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(CalculateCycleTimeInput input, CalculateCycleTimeOutput output) {
        List<FlowEvent> flowEventPairList = flowEventRepository.getAll();

        Workflow workflow = WorkflowTransfer.WorkflowDTOToWorkflow(workflowRepository.getWorkflowById(input.getWorkflowId()));
        boolean isbool = false;
        List<String> stageIds = new ArrayList();
        for(Lane lane: workflow.getLaneList()){
            if(lane.getLaneId().equals(input.getFromLaneId())){
                isbool = true;
            }else if(lane.getLaneId().equals(input.getToLaneId())){
                stageIds.add(lane.getLaneId());
                break;
            }
            if(isbool) {
                stageIds.add(lane.getLaneId());
            }
        }

        CycleTimeCalculator cycleTimeCalculator = new CycleTimeCalculator(stageIds, flowEventPairList, input.getCardId());

        long diff = cycleTimeCalculator.process();
        domainEventBus.postAll(cycleTimeCalculator);

        output.setDiffDays(diff/(24*3600));
        output.setDiffHours(diff %(24*3600)/3600);
        output.setDiffMinutes(diff % 3600/60);
        output.setDiffSeconds(diff % 60);
    }
}
