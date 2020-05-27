package ddd.kanban.usecase.board.commit;

import ddd.kanban.domain.model.board.Board;
import ddd.kanban.usecase.board.entity.BoardEntity;
import ddd.kanban.usecase.board.mapper.BoardEntityMapper;
import ddd.kanban.usecase.repository.BoardRepository;

public class CommitWorkflowUseCase {
    private BoardRepository boardRepository;
    public CommitWorkflowUseCase(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    public void execute(CommitWorkflowInput commitWorkflowInput, CommitWorkflowOutput commitWorkflowOutput){
        BoardEntity boardEntity = boardRepository.findById(commitWorkflowInput.getBoardId());
        Board board = BoardEntityMapper.mappingBoardFrom(boardEntity);
        String workflowId = board.commitWorkflow(commitWorkflowInput.getWorkflowId());

        boardRepository.save(BoardEntityMapper.mappingBoardEntityFrom(board));

        commitWorkflowOutput.setBoardId(board.getId());
        commitWorkflowOutput.setWorkflowId(workflowId);
    }

}
