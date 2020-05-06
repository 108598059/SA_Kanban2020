package domain.usecase.swimlane.create;

import domain.model.aggregate.workflow.Lane;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.workflow.repository.IWorkflowRepository;

public class CreateSwimlaneUseCase {
    private IWorkflowRepository workflowRepository;
    public CreateSwimlaneUseCase(IWorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public void execute(CreateSwimlaneUseCaseInput input, CreateSwimlaneUseCaseOutput output) {
        Workflow workflow = workflowRepository.getWorkflowById(input.getWorkflowId());
        Lane swimlane = workflow.createSwimlane(input.getSwimlaneName());

//        workflow.addLane(swimlane);
        workflowRepository.save(workflow);

        output.setWorkflowId(swimlane.getWorkflowId());
        output.setSwimlaneName(swimlane.getLaneName());
        output.setSwimlaneId(swimlane.getLaneId());
    }
}
