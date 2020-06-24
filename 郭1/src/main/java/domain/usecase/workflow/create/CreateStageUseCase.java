package domain.usecase.workflow.create;

import domain.entity.DomainEventBus;
import domain.entity.aggregate.workflow.Workflow;
import domain.usecase.workflow.WorkflowRepository;
import domain.usecase.workflow.WorkflowTransformer;


public class CreateStageUseCase {

    private WorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public CreateStageUseCase(WorkflowRepository workflowRepository, DomainEventBus eventBus){
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }


    public void execute(CreateStageInput createStageInput, CreateStageOutput createStageOutput) {

        Workflow workflow = WorkflowTransformer.toWorkflow(workflowRepository.getWorkFlowById(createStageInput.getWorkflowId()));

        String stageId = workflow.createStage(createStageInput.getStageName());
        workflowRepository.save(WorkflowTransformer.toDTO(workflow));
        eventBus.postAll(workflow);

        createStageOutput.setStageId(stageId) ;
    }


}
