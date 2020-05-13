package kanban.domain.usecase.board.get;

import kanban.domain.usecase.board.repository.IBoardRepository;
import kanban.domain.usecase.entity.BoardEntity;

import java.util.List;

public class GetBoardsByUserIdUseCase {

    private IBoardRepository boardRepository;

    public GetBoardsByUserIdUseCase(IBoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void execute(GetBoardsByUserIdInput input, GetBoardsByUserIdOutput output) {
        List<BoardEntity> boardEntities = boardRepository.getBoardsByUserId(input.getUserId());
        output.setBoardEntities(boardEntities);
    }
}
