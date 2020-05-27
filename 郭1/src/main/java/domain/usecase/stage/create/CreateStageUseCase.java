package domain.usecase.stage.create;

import domain.entity.DomainEventBus;
import domain.entity.workflow.Workflow;
import domain.usecase.workflow.WorkflowRepository;


public class CreateStageUseCase {

    private WorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public CreateStageUseCase(WorkflowRepository workflowRepository, DomainEventBus eventBus){
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }


    public void execute(CreateStageInput createStageInput, CreateStageOutput createStageOutput) {

        Workflow workflow = workflowRepository.getWorkFlowById(createStageInput.getWorkflowId());

        String stageId = workflow.createStage(createStageInput.getStageName());
        workflowRepository.save(workflow);
        eventBus.postAll(workflow);

        createStageOutput.setStageId(stageId) ;
    }


}
