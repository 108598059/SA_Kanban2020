package ddd.kanban.usecase.kanbanboard.workflow.create;

import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.kanbanboard.workflow.Workflow;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.kanbanboard.workflow.mapper.WorkflowEntityMapper;

import java.util.UUID;

public class CreateWorkflowUseCase {
    private WorkflowRepository workflowRepository;
    private DomainEventBus domainEventBus;

    public CreateWorkflowUseCase(WorkflowRepository workflowRepository, DomainEventBus domainEventBus){
        this.workflowRepository = workflowRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(CreateWorkflowInput createWorkflowInput, CreateWorkflowOutput createWorkflowOutput) {
        final String DEFAULT_COLUMN_NAME = "Default Column";
        Workflow workflow = new Workflow(UUID.randomUUID().toString(), createWorkflowInput.getWorkflowTitle(), createWorkflowInput.getBoardId());
        String defaultColumnId = workflow.createColumn(DEFAULT_COLUMN_NAME, workflow.getId());

        workflowRepository.add(WorkflowEntityMapper.mappingWorkflowEntityFrom(workflow));

        domainEventBus.postAll(workflow);

        createWorkflowOutput.setWorkflowId(workflow.getId());
        createWorkflowOutput.setWorkflowTitle(workflow.getTitle());
        createWorkflowOutput.setDefaultColumnId(defaultColumnId);
        createWorkflowOutput.setWorkflowBoardId(workflow.getBoardId());
    }
}