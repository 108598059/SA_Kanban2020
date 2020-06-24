package domain.usecase.board.create;

import domain.entity.DomainEventBus;
import domain.entity.aggregate.board.Board;
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

        // new a board and add a boardCreated evvent to itself(inside Aggregate)
        Board board = new Board(createBoardInput.getTeamId());
        board.setName(createBoardInput.getName());

        BoardDTO boardDTO = BoardTransformer.toDTO(board);
        boardRepository.add(boardDTO);

        // post the events, if someone is interest in, write a subscriber in Handler to listen
        eventBus.postAll(board);

        createBoardOutput.setBoardId(board.getId());
        createBoardOutput.setBoardName(createBoardInput.getName());
    }
}
