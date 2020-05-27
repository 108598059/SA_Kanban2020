package domain.usecase.board.create;

import domain.entity.DomainEventBus;
import domain.entity.board.Board;
import domain.usecase.board.BoardDTO;
import domain.usecase.board.BoardRepository;
import domain.usecase.board.BoardTransformer;

public class CreateBoardUseCase {

    private BoardRepository boardRepository;
    private DomainEventBus eventBus;

    public CreateBoardUseCase(BoardRepository boardRepository, DomainEventBus eventBus){
        this.boardRepository = boardRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateBoardInput createBoardInput, CreateBoardOutput createBoardOutput) {

        Board board = new Board();
        board.setName(createBoardInput.getName());

        BoardDTO boardDTO = BoardTransformer.toDTO(board);
        boardRepository.add(boardDTO);
        eventBus.postAll(board);

        createBoardOutput.setBoardId(board.getId());
        createBoardOutput.setBoardName(createBoardInput.getName());
    }
}
