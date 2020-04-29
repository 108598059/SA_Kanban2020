package domain.usecase.workflow;

import com.google.common.eventbus.Subscribe;
import domain.model.aggregate.board.Board;
import domain.model.aggregate.workflow.event.WorkflowCreated;
import domain.usecase.board.repository.IBoardRepository;

public class WorkflowEventHandler {
    private IBoardRepository boardRepository;

    public WorkflowEventHandler(IBoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Subscribe
    public void commitWorkflowHandleEvent(WorkflowCreated workflowCreated){

        Board board = boardRepository.getBoardById(workflowCreated.getBoardId());
        board.addWorkflowId(workflowCreated.getWorkflowId());

        boardRepository.save(board);
    }
}
