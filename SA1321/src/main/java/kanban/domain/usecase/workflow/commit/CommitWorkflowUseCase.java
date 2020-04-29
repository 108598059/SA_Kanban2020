package kanban.domain.usecase.workflow.commit;

import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.board.Board;
import kanban.domain.usecase.board.repository.IBoardRepository;

public class CommitWorkflowUseCase {

    private IBoardRepository boardRepository;

    public CommitWorkflowUseCase(IBoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void execute(CommitWorkflowInput input, CommitWorkflowOutput output) {
        Board board = boardRepository.getBoardById(input.getBoardId());
        String workflowId = board.commitWorkflow(input.getWorkflowId());

        boardRepository.save(board);
        output.setWorkflowId(workflowId);

    }
}
