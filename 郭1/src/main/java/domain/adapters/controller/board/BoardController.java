package domain.adapters.controller.board;

import domain.adapters.repository.BoardRepositoryImpl;
import domain.adapters.presenter.BoardPresenter;
import domain.usecase.board.BoardRepository;
import domain.usecase.board.create.CreateBoardInput;
import domain.usecase.board.create.CreateBoardOutput;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.adapters.View;


public class BoardController {



    public BoardController(){
    }

    public void handleCreateBoard(View view, String boardName){

        BoardRepository boardRepository = new BoardRepositoryImpl();

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);

        CreateBoardInput createBoardInput = new CreateBoardInputImpl();
        CreateBoardOutput createBoardOutput = new BoardPresenter(view);

        createBoardInput.setName(boardName);

        createBoardUseCase.execute(createBoardInput,createBoardOutput);



    }

}
