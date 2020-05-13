package ddd.kanban.usecase.board.commit;

import ddd.kanban.domain.model.board.Board;
import ddd.kanban.usecase.DTO.BoardDTO;
import ddd.kanban.usecase.DTOMapper;
import ddd.kanban.usecase.EntityMapper;
import ddd.kanban.usecase.repository.BoardRepository;

public class CommitWorkflowUseCase {
    private BoardRepository boardRepository;
    private EntityMapper entityMapper;
    private DTOMapper dtoMapper;
    public CommitWorkflowUseCase(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
        this.entityMapper = new EntityMapper();
        this.dtoMapper = new DTOMapper();
    }

    public void execute(CommitWorkflowInput commitWorkflowInput, CommitWorkflowOutput commitWorkflowOutput){
        BoardDTO boardDTO = boardRepository.findById(commitWorkflowInput.getBoardId());
        Board board = entityMapper.mappingBoardEntityFrom(boardDTO);
        String workflowId = board.commitWorkflow(commitWorkflowInput.getWorkflowId());

        boardRepository.save(dtoMapper.mappingBoardDTOFrom(board));

        commitWorkflowOutput.setBoardId(board.getId());
        commitWorkflowOutput.setWorkflowId(workflowId);
    }

}
