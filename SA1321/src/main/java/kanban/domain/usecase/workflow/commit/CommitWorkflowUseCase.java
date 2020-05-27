package kanban.domain.usecase.workflow.commit;

import kanban.domain.model.aggregate.board.Board;
import kanban.domain.usecase.board.mapper.BoardEntityModelMapper;
import kanban.domain.usecase.board.repository.IBoardRepository;

public class CommitWorkflowUseCase implements CommitWorkflowInput {

    private IBoardRepository boardRepository;
    private String boardId;
    private String workflowId;

    public CommitWorkflowUseCase(IBoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void execute(CommitWorkflowInput input, CommitWorkflowOutput output) {
        Board board = BoardEntityModelMapper.transformEntityToModel(boardRepository.getBoardById(input.getBoardId()));
        String workflowId = board.commitWorkflow(input.getWorkflowId());

        boardRepository.save(BoardEntityModelMapper.transformModelToEntity(board));
        output.setWorkflowId(workflowId);
    }

    @Override
    public String getBoardId() {
        return boardId;
    }

    @Override
    public void setBoardId(String boardId) {
        this.boardId = boardId;
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
