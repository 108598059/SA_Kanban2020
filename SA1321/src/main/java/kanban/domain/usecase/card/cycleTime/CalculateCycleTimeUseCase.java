package kanban.domain.usecase.card.cycleTime;

import kanban.domain.model.DomainEventBus;
import kanban.domain.model.FlowEvent;
import kanban.domain.model.aggregate.workflow.Stage;
import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.model.service.cycleTime.CalculateCycleTimeCalculater;
import kanban.domain.usecase.flowEvent.IFlowEventRepository;
import kanban.domain.usecase.workflow.mapper.WorkflowEntityModelMapper;
import kanban.domain.usecase.workflow.IWorkflowRepository;

import java.util.*;

public class CalculateCycleTimeUseCase implements CalculateCycleTimeInput {
    private String workflowId;
    private String cardId;
    private String beginningStageId;
    private String endingStageId;

    private IWorkflowRepository workflowRepository;
    private IFlowEventRepository flowEventRepository;
    private DomainEventBus domainEventBus;

    public CalculateCycleTimeUseCase(IWorkflowRepository workflowRepository, IFlowEventRepository flowEventRepository, DomainEventBus domainEventBus) {
        this.workflowRepository = workflowRepository;
        this.flowEventRepository = flowEventRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(CalculateCycleTimeInput input, CalculateCycleTimeOutput output) {

        List<String> boundaryStageIds = getBoundaryStageIds();

        List<FlowEvent> flowEvents = flowEventRepository.getAll();

        CalculateCycleTimeCalculater calculateCycleTimeCalculater =
                new CalculateCycleTimeCalculater(
                        boundaryStageIds,
                        flowEvents,
                        input.getCardId());

        CycleTimeModel cycleTimeModel = new CycleTimeModel(calculateCycleTimeCalculater.process());

        domainEventBus.postAll(calculateCycleTimeCalculater);
        output.setCycleTimeModel(cycleTimeModel);
    }

    private List<String> getBoundaryStageIds() {
        List<String> stageIds = new ArrayList();
        Workflow workflow = WorkflowEntityModelMapper.transformEntityToModel(
                workflowRepository.getWorkflowById(getWorkflowId()));
        boolean isInBoundary = false;

        for(Stage stage: workflow.getStages()){
            if(stage.getStageId().equals(getBeginningStageId())){
                isInBoundary = true;
            }else if(stage.getStageId().equals(getEndingStageId())){
                stageIds.add(stage.getStageId());
                break;
            }
            if(isInBoundary) {
                stageIds.add(stage.getStageId());
            }
        }
        return stageIds;
    }

    @Override
    public String getWorkflowId() {
        return workflowId;
    }

    @Override
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    @Override
    public String getCardId() {
        return cardId;
    }

    @Override
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    @Override
    public String getBeginningStageId() {
        return beginningStageId;
    }

    @Override
    public void setBeginningStageId(String beginningStageId) {
        this.beginningStageId = beginningStageId;
    }

    @Override
    public String getEndingStageId() {
        return endingStageId;
    }

    @Override
    public void setEndingStageId(String endingStageId) {
        this.endingStageId = endingStageId;
    }
}
