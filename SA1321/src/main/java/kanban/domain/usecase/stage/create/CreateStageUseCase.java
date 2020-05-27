package kanban.domain.usecase.stage.create;

import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.workflow.mapper.WorkflowEntityModelMapper;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;

public class CreateStageUseCase implements CreateStageInput {
    private IWorkflowRepository workflowRepository;

    private String stageName;
    private String workflowId;

    public CreateStageUseCase(IWorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public void execute(CreateStageInput input, CreateStageOutput output) {

        Workflow workflow = WorkflowEntityModelMapper.transformEntityToModel(
                workflowRepository.getWorkflowById(input.getWorkflowId()));

        String stageId = workflow.createStage(input.getStageName());
        output.setStageId(stageId);

        workflowRepository.save(WorkflowEntityModelMapper.transformModelToEntity(workflow));
    }

    @Override
    public String getStageName() {
        return stageName;
    }

    @Override
    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    @Override
    public String getWorkflowId() {
        return workflowId;
    }

    @Override
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }
}
