package domain.usecase.workflow;

import com.google.common.eventbus.Subscribe;
import domain.entity.board.Board;
import domain.entity.workflow.event.WorkflowCreated;
import domain.usecase.board.BoardRepository;

public class WorkflowEventHandler {

    private BoardRepository boardRepository;

    public WorkflowEventHandler(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Subscribe
    public void commitWorkflowHandleEvent(WorkflowCreated workflowCreated){

        Board board = boardRepository.getBoardById(workflowCreated.getBoardId());

        board.add(workflowCreated.getWorkflowId());

        boardRepository.save(board);
    }
}
