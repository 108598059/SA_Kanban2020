package domain.usecase.board.commit;

import domain.entity.DomainEventBus;
import domain.entity.board.Board;
import domain.usecase.board.BoardDTO;
import domain.usecase.board.BoardRepository;
import domain.usecase.board.BoardTransformer;

public class CommitWorkflowUseCase {

    private BoardRepository boardRepository;
    private DomainEventBus eventBus;

    public CommitWorkflowUseCase(BoardRepository boardRepository, DomainEventBus eventBus) {
        this.boardRepository = boardRepository;
        this.eventBus = eventBus;
    }

    public void execute(CommitWorkflowInput commitWorkflowInput, CommitWorkflowOutput commitWorkflowOutput){

        BoardDTO boardDTO = boardRepository.getBoardById(commitWorkflowInput.getBoardId());

        Board board = BoardTransformer.toBoard(boardDTO);
        board.add(commitWorkflowInput.getWorkflowId());
        boardDTO = BoardTransformer.toDTO(board);

        boardRepository.save(boardDTO);
        eventBus.postAll(board);

        commitWorkflowOutput.setWorkflowId(commitWorkflowInput.getWorkflowId());
    }
}
