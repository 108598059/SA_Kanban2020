package domain.usecase.workflow.create;

import domain.model.DomainEvent;
import domain.model.aggregate.DomainEventBus;
import domain.model.aggregate.board.Board;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.workflow.repository.IWorkflowRepository;

public class CreateWorkflowUseCase {
    private IWorkflowRepository workflowRepository;
//    private IBoardRepository boardRepository;
    private DomainEventBus eventBus;

    public CreateWorkflowUseCase(IWorkflowRepository workflowRepository, IBoardRepository boardRepository, DomainEventBus eventBus) {
        this.eventBus = eventBus;
        this.workflowRepository = workflowRepository;
//        this.boardRepository = boardRepository;
    }

    public void execute(CreateWorkflowUseCaseInput input, CreateWorkflowUseCaseOutput output) {
//        Board board = boardRepository.getBoardById(input.getBoardId());
        Workflow workflow = new Workflow(input.getWorkflowName());

        workflowRepository.add(workflow);
//        board.addWorkflowId(workflow.getWorkflowId());
//        boardRepository.save(board);

        eventBus.postAll(workflow);

        output.setWorkflowId(workflow.getWorkflowId());
        output.setWorkflowName(workflow.getWorkflowName());
    }
}
