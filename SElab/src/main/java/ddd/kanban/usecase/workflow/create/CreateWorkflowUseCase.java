package ddd.kanban.usecase.workflow.create;

import ddd.kanban.domain.model.workflow.Column;
import ddd.kanban.domain.model.workflow.Workflow;
import ddd.kanban.usecase.repository.WorkflowRepository;

import java.util.UUID;

public class CreateWorkflowUseCase {
    private WorkflowRepository workflowRepository;
    public CreateWorkflowUseCase(WorkflowRepository workflowRepository){
        this.workflowRepository = workflowRepository;
    }

    public void execute(CreateWorkflowInput createWorkflowInput, CreateWorkflowOutput createWorkflowOutput) {
        Workflow workflow = new Workflow(UUID.randomUUID().toString(), createWorkflowInput.getWorkflowTitle(), createWorkflowInput.getBoardId());

        workflowRepository.add(workflow);
        workflowRepository.save();

        createWorkflowOutput.setWorkflowId(workflow.getId());
        createWorkflowOutput.setWorkflowTitle(workflow.getTitle());

    }

}