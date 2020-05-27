package domain.usecase.workflow.commit;

import domain.model.aggregate.DomainEventBus;
import domain.model.aggregate.board.Board;
import domain.usecase.board.BoardTransfer;
import domain.usecase.board.repository.IBoardRepository;

public class CommitWorkflowUseCase {
    private IBoardRepository boardRepository;
    private DomainEventBus eventBus;

    public CommitWorkflowUseCase(IBoardRepository boardRepository, DomainEventBus eventBus) {
        this.eventBus = eventBus;
        this.boardRepository = boardRepository;
    }

    public void execute(CommitWorkflowUseCaseInput input, CommitWorkflowUseCaseOutput output) {
        Board board = BoardTransfer.BoardEntityToBoard(boardRepository.getBoardById(input.getBoardId()));
        board.addWorkflowId(input.getWorkflowId());

        boardRepository.save(BoardTransfer.BoardToBoardEntity(board));

        eventBus.postAll(board);

        output.setWorkflowId(input.getWorkflowId());
    }
}
