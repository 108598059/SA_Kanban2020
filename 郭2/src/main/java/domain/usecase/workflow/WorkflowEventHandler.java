package domain.usecase.workflow;

import com.google.common.eventbus.Subscribe;
import domain.model.aggregate.board.Board;
import domain.model.aggregate.workflow.event.WorkflowCreated;
import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.workflow.commit.CommitWorkflowUseCase;
import domain.usecase.workflow.commit.CommitWorkflowUseCaseInput;
import domain.usecase.workflow.commit.CommitWorkflowUseCaseOutput;

public class WorkflowEventHandler {
    private IBoardRepository boardRepository;

    public WorkflowEventHandler(IBoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Subscribe
    public void commitWorkflowHandleEvent(WorkflowCreated workflowCreated){
        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository);
        CommitWorkflowUseCaseInput input = new CommitWorkflowUseCaseInput();
        CommitWorkflowUseCaseOutput output = new CommitWorkflowUseCaseOutput();

        input.setBoardId(workflowCreated.getBoardId());
        input.setWorkflowId(workflowCreated.getWorkflowId());

        commitWorkflowUseCase.execute(input, output);
    }
}
