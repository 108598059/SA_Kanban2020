package tw.teddysoft.clean.usecase.kanbanboard.workflow.create;

import tw.teddysoft.clean.domain.model.DomainEventBus;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.Workflow;
import tw.teddysoft.clean.domain.usecase.UseCase;
import tw.teddysoft.clean.domain.usecase.repository.Repository;

public class CreateWorkflowUseCase implements UseCase<CreateWorkflowInput, CreateWorkflowOutput> {

    private final Repository<Workflow> workflowRepository;
    private final DomainEventBus eventBus;

    public CreateWorkflowUseCase(Repository workflowRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }

    @Override
    public CreateWorkflowInput createInput(){
        return new CreateWorkflowInputImpl();
    }

    @Override
    public void execute(CreateWorkflowInput input, CreateWorkflowOutput output) {

        Workflow workflow = new Workflow(input.getWorkflowName(), input.getBoardId());
        workflowRepository.save(workflow);
        eventBus.postAll(workflow);


//        // TODO: commit the workflow to its board
//        Board board = boardRepository.findById(input.getBoardId());
//        board.commitWorkflow(workflow.getId());
//        boardRepository.save(board);

        output.setWorkflowId(workflow.getId());
        output.setWorkflowName(workflow.getName());
    }


    private static class CreateWorkflowInputImpl implements CreateWorkflowInput{

        private String workflowName;
        private String boardId;

        @Override
        public String getWorkflowName() {
            return workflowName;
        }

        @Override
        public CreateWorkflowInput setWorkflowName(String workflowName) {
            this.workflowName = workflowName;
            return this;
        }

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public CreateWorkflowInput setBoardId(String boardId) {
            this.boardId = boardId;
            return this;
        }
    }
}
