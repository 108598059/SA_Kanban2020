package domain.usecase.board.create;

import domain.model.aggregate.board.Board;
import domain.usecase.board.BoardTransfer;
import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.board.BoardEntity;

public class CreateBoardUseCase {
    private IBoardRepository boardRepository;
    private Board board;

    public CreateBoardUseCase(IBoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void execute(CreateBoardUseCaseInput input, CreateBoardUseCaseOutput output) {
        board = new Board(input.getBoardName());

        BoardEntity boardEntity = BoardTransfer.BoardToBoardEntity(board);

        boardRepository.add(boardEntity);

        output.setBoardId(boardEntity.getBoardId());
        output.setBoardName(boardEntity.getBoardName());
    }
}
