package ddd.kanban.usecase.workflow.create;

import ddd.kanban.domain.model.workflow.Workflow;
import ddd.kanban.usecase.repository.WorkflowRepository;

public class CreateColumnUseCase {
    private WorkflowRepository workflowRepository;

    public CreateColumnUseCase(WorkflowRepository workflowRepository){
        this.workflowRepository = workflowRepository;
    }

    public void execute(CreateColumnInput createColumnInput, CreateColumnOutput createColumnOutput) {
        Workflow workflow = workflowRepository.findById(createColumnInput.getWorkflowId());
        String laneId = workflow.createColumn(createColumnInput.getColumnTitle(), createColumnInput.getWorkflowId());
        createColumnOutput.setColumnId(laneId);

        workflowRepository.add(workflow);
        workflowRepository.save();
    }
}
