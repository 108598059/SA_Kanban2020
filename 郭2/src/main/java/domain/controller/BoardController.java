package domain.controller;

import domain.adapter.repository.board.MySqlBoardRepository;
import domain.model.aggregate.board.Board;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.board.create.CreateBoardUseCaseInput;
import domain.usecase.board.create.CreateBoardUseCaseOutput;
import domain.usecase.board.repository.IBoardRepository;

import javax.servlet.http.HttpServletRequest;

public class BoardController {
    private IBoardRepository boardRepository = new MySqlBoardRepository();
    private HttpServletRequest request;

    public BoardController(HttpServletRequest request){
        this.request = request;
    }

    public void createBoard() {

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardUseCaseInput input = new CreateBoardUseCaseInput();
        CreateBoardUseCaseOutput output = new CreateBoardUseCaseOutput();

        input.setBoardName(request.getParameter("boardName"));

        createBoardUseCase.execute(input, output);

        request.setAttribute("boardId", output.getBoardId());
        request.setAttribute("boardName", output.getBoardName());
    }
}
