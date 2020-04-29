package domain.usecase.stage.create;

import domain.entity.workflow.Workflow;
import domain.adapter.WorkflowRepositoryImpl;
import domain.usecase.workflow.WorkflowRepository;

public class CreateStageUseCase {

    private WorkflowRepository workflowRepository;

    public CreateStageUseCase(WorkflowRepository workflowRepository){
        this.workflowRepository = workflowRepository;
    }

    public void execute(CreateStageInput createStageInput, CreateStageOutput createStageOutput) {

        Workflow workflow = workflowRepository.getWorkFlowById(createStageInput.getWorkflowId());

        String stageId = workflow.createStage(createStageInput.getStageName());
        workflowRepository.save(workflow);

        createStageOutput.setStageId(stageId) ;
    }


}
