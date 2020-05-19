package domain.adapters.controller.board;

import domain.adapters.repository.BoardRepositoryImpl;
import domain.adapters.presenter.BoardPresenter;
import domain.usecase.board.BoardRepository;
import domain.usecase.board.create.CreateBoardInput;
import domain.usecase.board.create.CreateBoardOutput;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.adapters.View;


public class BoardController {

    private BoardRepository boardRepository;
    private BoardPresenter presenter;

    public BoardController(BoardRepository boardRepository, BoardPresenter presenter){
        this.boardRepository = boardRepository;
        this.presenter = presenter;
    }

    public void createBoard(String boardName){

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);

        CreateBoardInput createBoardInput = new CreateBoardInputImpl();

        createBoardInput.setName(boardName);

        createBoardUseCase.execute(createBoardInput,presenter);



    }

}
