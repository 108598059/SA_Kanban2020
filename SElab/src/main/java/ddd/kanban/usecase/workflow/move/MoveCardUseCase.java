package ddd.kanban.usecase.workflow.move;

import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.workflow.Workflow;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.workflow.WorkflowEntityMapper;

public class MoveCardUseCase {
    private WorkflowRepository workflowRepository;
    private DomainEventBus domainEventBus;

    public MoveCardUseCase(WorkflowRepository workflowRepository, DomainEventBus domainEventBus){
        this.workflowRepository = workflowRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(MoveCardInput moveCardInput, MoveCardOutput moveCardOutput) {
        Workflow workflow = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findById(moveCardInput.getWorkflowId()));
        String cardId = workflow.moveCard(moveCardInput.getCardId(), moveCardInput.getFromLaneId(), moveCardInput.getToLaneId());

        workflowRepository.save(WorkflowEntityMapper.mappingWorkflowEntityFrom(workflow));

        domainEventBus.postAll(workflow);
        moveCardOutput.setCardId(cardId);
    }
}
