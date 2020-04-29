package ddd.kanban.usecase.board.create;


import ddd.kanban.domain.model.Board.Board;
import ddd.kanban.usecase.repository.BoardRepository;


import java.util.UUID;

public class CreateBoardUseCase {

    private BoardRepository boardRepository;

    public CreateBoardUseCase(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }


    public void execute(CreateBoardInput createBoardInput, CreateBoardOutput createBoardOutput) {
        Board board = new Board(createBoardInput.getBoardId(), createBoardInput.getBoardName(), createBoardInput.getBoardDescription());
        boardRepository.add(board);
        boardRepository.save();

        createBoardOutput.setBoardId(board.getId());
        createBoardOutput.setBoardName(board.getName());
        createBoardOutput.setBoardDescription(board.getDescription());
    }
}
