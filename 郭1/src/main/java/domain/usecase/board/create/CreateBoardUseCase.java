package domain.usecase.board.create;

import domain.entity.board.Board;
import domain.usecase.board.BoardRepository;

public class CreateBoardUseCase {

    private BoardRepository boardRepository;

    public CreateBoardUseCase(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    public void execute(CreateBoardInput createBoardInput, CreateBoardOutput createBoardOutput) {
        Board board = new Board();

        board.setName(createBoardInput.getName());
        boardRepository.save(board);


        createBoardOutput.setBoardId(board.getId());

    }
}
