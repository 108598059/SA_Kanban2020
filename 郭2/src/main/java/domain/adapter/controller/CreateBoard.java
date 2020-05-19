package domain.adapter.controller;

import domain.adapter.presenter.CreateBoardUseCasePresenter;
import domain.adapter.repository.board.MySqlBoardRepository;
import domain.adapter.view_model.ViewModel;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.board.create.CreateBoardUseCaseInput;
import domain.usecase.board.create.CreateBoardUseCaseOutput;
import domain.usecase.board.repository.IBoardRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board")
public class CreateBoard extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IBoardRepository boardRepository = new MySqlBoardRepository();
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardUseCaseInput input = new CreateBoardUseCaseInput();
        CreateBoardUseCaseOutput output = new CreateBoardUseCaseOutput();


        input.setBoardName(request.getParameter("boardName"));

        createBoardUseCase.execute(input, output);

        CreateBoardUseCasePresenter presenter = new CreateBoardUseCasePresenter(output);

        ViewModel viewModel = presenter.createView();

        request.setAttribute("createBoard", viewModel);

        request.getRequestDispatcher("WEB-INF/view/board.jsp").forward(request, response);
    }
}
