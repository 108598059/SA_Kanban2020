package domain.usecase.board.create;

import domain.model.DomainEventBus;
import domain.model.aggregate.board.Board;
import domain.usecase.board.BoardTransfer;
import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.board.BoardDTO;

public class CreateBoardUseCase {
    private IBoardRepository boardRepository;
    private Board board;
    private DomainEventBus eventBus;

    public CreateBoardUseCase(IBoardRepository boardRepository, DomainEventBus eventBus) {
        this.boardRepository = boardRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateBoardUseCaseInput input, CreateBoardUseCaseOutput output) {
        board = new Board(input.getUserId(), input.getBoardName());

        BoardDTO boardDTO = BoardTransfer.BoardToBoardDTO(board);

        boardRepository.add(boardDTO);
        eventBus.postAll(board);


        output.setBoardId(boardDTO.getBoardId());
        output.setBoardName(boardDTO.getBoardName());
    }
}
