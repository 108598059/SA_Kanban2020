package domain.usecase.workflow;

import com.google.common.eventbus.Subscribe;
import domain.model.aggregate.DomainEventBus;
import domain.model.aggregate.board.Board;
import domain.model.aggregate.workflow.event.WorkflowCreated;
import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.workflow.commit.CommitWorkflowUseCase;
import domain.usecase.workflow.commit.CommitWorkflowUseCaseInput;
import domain.usecase.workflow.commit.CommitWorkflowUseCaseOutput;

public class WorkflowEventHandler {
    private IBoardRepository boardRepository;
    private DomainEventBus eventBus;

    public WorkflowEventHandler(IBoardRepository boardRepository, DomainEventBus eventBus) {
        this.eventBus = eventBus;
        this.boardRepository = boardRepository;
    }

    @Subscribe
    public void commitWorkflowHandleEvent(WorkflowCreated workflowCreated){
        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository, eventBus);
        CommitWorkflowUseCaseInput input = new CommitWorkflowUseCaseInput();
        CommitWorkflowUseCaseOutput output = new CommitWorkflowUseCaseOutput();

        input.setBoardId(workflowCreated.getBoardId());
        input.setWorkflowId(workflowCreated.getWorkflowId());

        commitWorkflowUseCase.execute(input, output);
    }
}
