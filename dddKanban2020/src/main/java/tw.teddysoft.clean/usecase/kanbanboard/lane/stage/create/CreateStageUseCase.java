package tw.teddysoft.clean.usecase.kanbanboard.lane.stage.create;

import tw.teddysoft.clean.domain.model.DomainEventBus;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.Lane;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.Workflow;
import tw.teddysoft.clean.domain.usecase.UseCase;
import tw.teddysoft.clean.domain.usecase.repository.Repository;

public class CreateStageUseCase implements
        UseCase<CreateStageInput, CreateStageOutput> {

    private Repository<Workflow> repository;
    private DomainEventBus eventBus;

    public CreateStageUseCase(Repository repository,
                                  DomainEventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public CreateStageInput createInput(){
        return new CreateStageInputImpl();
    }

    @Override
    public void execute(CreateStageInput input, CreateStageOutput output) {
        Workflow workflow = repository.findById(input.getWorkflowId());

        Lane stage;
        if (input.isTopStage())
            stage = workflow.addStage(input.getName());
        else
            stage = workflow.addStage(input.getParentId(), input.getName());

        repository.save(workflow);
        eventBus.postAll(workflow);

        output.setId(stage.getId());
    }


    private static class CreateStageInputImpl implements CreateStageInput {

        private String workflowId;
        private String title;
        private String parentId;

        CreateStageInputImpl(){
            parentId = null;
        }

        @Override
        public void setWorkflowId(String workflowId) {
            this.workflowId = workflowId;
        }

        @Override
        public void setName(String name) {
            this.title = name;
        }

        @Override
        public String getWorkflowId() {
            return workflowId;
        }

        @Override
        public String getName(){
            return this.title;
        }

        @Override
        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        @Override
        public String getParentId() {
            return parentId;
        }

        @Override
        public boolean isTopStage() {
            return parentId == null ? true : false;
        }
    }
}