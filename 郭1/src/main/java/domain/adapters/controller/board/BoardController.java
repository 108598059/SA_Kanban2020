package domain.adapters.controller.board;

import domain.adapters.controller.board.input.CreateBoardInputImpl;
import domain.adapters.presenter.BoardPresenter;
import domain.adapters.viewmodel.board.BoardViewModel;

import domain.usecase.board.create.CreateBoardInput;

import domain.usecase.board.create.CreateBoardUseCase;



public class BoardController {

    private BoardPresenter presenter;
    private CreateBoardUseCase createBoardUseCase;

    public BoardController(CreateBoardUseCase createBoardUseCase, BoardPresenter presenter){
        this.presenter = presenter;
        this.createBoardUseCase = createBoardUseCase;
    }

    public BoardViewModel createBoard(String boardName){

        CreateBoardInput createBoardInput = new CreateBoardInputImpl();
        createBoardInput.setName(boardName);

        createBoardUseCase.execute(createBoardInput,presenter);

        return presenter.createBoardViewModel();

    }

}
