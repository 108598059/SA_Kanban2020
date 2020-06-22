package ddd.kanban.usecase.kanbanboard.workflow.create;

import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.kanbanboard.workflow.Workflow;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.kanbanboard.workflow.mapper.WorkflowEntityMapper;

public class CreateColumnUseCase {
    private WorkflowRepository workflowRepository;
    private DomainEventBus domainEventBus;

    public CreateColumnUseCase(WorkflowRepository workflowRepository, DomainEventBus domainEventBus){
        this.workflowRepository = workflowRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(CreateColumnInput createColumnInput, CreateColumnOutput createColumnOutput) {
        Workflow workflow = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findById(createColumnInput.getWorkflowId()));
        String laneId = workflow.createColumn(createColumnInput.getColumnTitle(), createColumnInput.getWorkflowId());
        createColumnOutput.setColumnId(laneId);

        workflowRepository.save(WorkflowEntityMapper.mappingWorkflowEntityFrom(workflow));

        domainEventBus.postAll(workflow);
    }
}
