package ddd.kanban.usecase.kanbanboard.workflow.commit;

import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.kanbanboard.board.Board;
import ddd.kanban.usecase.kanbanboard.board.entity.BoardEntity;
import ddd.kanban.usecase.kanbanboard.board.mapper.BoardEntityMapper;
import ddd.kanban.usecase.repository.BoardRepository;

public class CommitWorkflowUseCase {
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public CommitWorkflowUseCase(BoardRepository boardRepository, DomainEventBus domainEventBus){
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(CommitWorkflowInput commitWorkflowInput, CommitWorkflowOutput commitWorkflowOutput){
        BoardEntity boardEntity = boardRepository.findById(commitWorkflowInput.getBoardId());
        Board board = BoardEntityMapper.mappingBoardFrom(boardEntity);
        String workflowId = board.commitWorkflow(commitWorkflowInput.getWorkflowId());

        boardRepository.save(BoardEntityMapper.mappingBoardEntityFrom(board));

        domainEventBus.postAll(board);

        commitWorkflowOutput.setBoardId(board.getId());
        commitWorkflowOutput.setWorkflowId(workflowId);
    }

}
