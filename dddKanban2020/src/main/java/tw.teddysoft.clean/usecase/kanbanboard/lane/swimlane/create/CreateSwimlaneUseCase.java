package tw.teddysoft.clean.usecase.kanbanboard.lane.swimlane.create;

import tw.teddysoft.clean.domain.model.DomainEventBus;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.Lane;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.Workflow;
import tw.teddysoft.clean.domain.usecase.UseCase;
import tw.teddysoft.clean.domain.usecase.repository.Repository;

public class CreateSwimlaneUseCase implements
        UseCase<CreateSwimlaneInput, CreateSwimlaneOutput> {

    private Repository<Workflow> repository;
    private DomainEventBus eventBus;

    public CreateSwimlaneUseCase(Repository repository, DomainEventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public CreateSwimlaneInput createInput(){
        return new CreateSwimlaneInputImpl();
    }

    @Override
    public void execute(CreateSwimlaneInput input, CreateSwimlaneOutput output) {

        Workflow workflow = repository.findById(input.getWorkflowId());
        Lane swimlane = workflow.addSwimlane(input.getParentId(), input.getTitle());
        repository.save(workflow);
        eventBus.postAll(workflow);

        output.setId(swimlane.getId());
    }

    private static class CreateSwimlaneInputImpl implements CreateSwimlaneInput {
        private String workflowId;
        private String laneName;
        private String parentId;

        @Override
        public void setWorkflowId(String workflowId) {
            this.workflowId = workflowId;
        }

        @Override
        public void setTitle(String title) {
            this.laneName = title;
        }

        @Override
        public String getWorkflowId() {
            return workflowId;
        }

        @Override
        public String getTitle(){
            return this.laneName;
        }

        @Override
        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        @Override
        public String getParentId() {
            return parentId;
        }
    }
}