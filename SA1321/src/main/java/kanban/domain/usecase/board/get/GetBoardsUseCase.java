package kanban.domain.usecase.board.get;

import kanban.domain.model.aggregate.board.Board;
import kanban.domain.usecase.board.BoardDTO;
import kanban.domain.usecase.board.BoardDTOModelTransformer;
import kanban.domain.usecase.board.BoardEntityModelTransformer;
import kanban.domain.usecase.board.repository.IBoardRepository;
import kanban.domain.usecase.board.BoardEntity;

import java.util.ArrayList;
import java.util.List;

public class GetBoardsUseCase {

    private IBoardRepository boardRepository;

    public GetBoardsUseCase(IBoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void execute(GetBoardsInput input, GetBoardsOutput output) {
        List<BoardEntity> boardEntities = boardRepository.getBoardsByUserId(input.getUserId());
        List<BoardDTO> BoardDTOs = new ArrayList<>();
        for(BoardEntity boardEntity: boardEntities){
            Board board = BoardEntityModelTransformer.transformEntityToModel(boardEntity);
            BoardDTOs.add(BoardDTOModelTransformer.transformModelToDTO(board));
        }
        output.setBoardDTOs(BoardDTOs);
    }
}
