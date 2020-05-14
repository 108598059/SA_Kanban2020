package kanban.domain.usecase.board.create;

import kanban.domain.model.aggregate.board.Board;
import kanban.domain.usecase.board.BoardEntityModelTransformer;
import kanban.domain.usecase.board.repository.IBoardRepository;

public class CreateBoardUseCase {
    private IBoardRepository boardRepository;

    public CreateBoardUseCase(IBoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void execute(CreateBoardInput input, CreateBoardOutput output) {
        Board board = new Board(input.getUserId(), input.getBoardName());

        boardRepository.add(BoardEntityModelTransformer.transformModelToEntity(board));
        output.setBoardId(board.getBoardId());
    }
}
