package domain.usecase.workflow.commit;

import domain.model.aggregate.board.Board;
import domain.usecase.board.BoardDTO;
import domain.usecase.board.repository.IBoardRepository;

public class CommitWorkflowUseCase {
    private IBoardRepository boardRepository;

    public CommitWorkflowUseCase(IBoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void execute(CommitWorkflowUseCaseInput input, CommitWorkflowUseCaseOutput output) {
        Board board = BoardDTO.BoardEntityToBoard(boardRepository.getBoardById(input.getBoardId()));
        board.addWorkflowId(input.getWorkflowId());

        boardRepository.save(BoardDTO.BoardToBoardEntity(board));

        output.setWorkflowId(input.getWorkflowId());
    }
}
