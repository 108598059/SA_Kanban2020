package domain.usecase.stage.create;

import domain.model.aggregate.workflow.Lane;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.workflow.repository.IWorkflowRepository;

public class CreateStageUseCase {
    private IWorkflowRepository workflowRepository;
    private Lane stage;

    public CreateStageUseCase(IWorkflowRepository workflowRepository){
        this.workflowRepository = workflowRepository;
    }

    public void execute(CreateStageUseCaseInput input, CreateStageUseCaseOutput output) {
        Workflow workflow = workflowRepository.getWorkflowById(input.getWorkflowId());
        stage = workflow.createStage(input.getStageName());

//        workflow.addLane(stage);
        workflowRepository.save(workflow);

        output.setWorkflowId(stage.getWorkflowId());
        output.setStageName(stage.getLaneName());
        output.setStageId(stage.getLaneId());
    }
}
