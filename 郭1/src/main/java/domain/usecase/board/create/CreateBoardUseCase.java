package domain.usecase.board.create;

import domain.entity.board.Board;
import domain.usecase.board.BoardDTO;
import domain.usecase.board.BoardRepository;
import domain.usecase.board.BoardTransformer;

public class CreateBoardUseCase {

    private BoardRepository boardRepository;

    public CreateBoardUseCase(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    public void execute(CreateBoardInput createBoardInput, CreateBoardOutput createBoardOutput) {

        Board board = new Board();
        board.setName(createBoardInput.getName());

        BoardDTO boardDTO = BoardTransformer.toDTO(board);
        boardRepository.add(boardDTO);


        createBoardOutput.setBoardId(board.getId());
        createBoardOutput.setBoardName(createBoardInput.getName());
    }
}
