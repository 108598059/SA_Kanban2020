package domain.usecase.swimlane.create;

import domain.entity.workflow.Workflow;
import domain.usecase.workflow.WorkflowRepository;

public class CreateSwimlaneUseCase {

    private WorkflowRepository workflowRepository;

    public CreateSwimlaneUseCase(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public void execute(CreateSwimlaneInput createSwimlaneInput, CreateSwimlaneOutput createSwimlaneOutput) {
        Workflow workflow = workflowRepository.getWorkFlowById(createSwimlaneInput.getWorkflowId());
        String swimlaneId = workflow.createSwimlane(createSwimlaneInput.getStageId(), createSwimlaneInput.getName());
        workflowRepository.save(workflow);
        createSwimlaneOutput.setSwimlaneId(swimlaneId);
    }
}
