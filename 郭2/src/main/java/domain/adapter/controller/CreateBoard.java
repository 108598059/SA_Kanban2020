package domain.adapter.controller;

import domain.adapter.presenter.CreateBoardUseCasePresenter;
import domain.adapter.view_model.ViewModel;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.board.create.CreateBoardUseCaseInput;
import domain.usecase.board.repository.IBoardRepository;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board")
public class CreateBoard extends HttpServlet {

    @Inject
    private IBoardRepository boardRepository;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardUseCaseInput input = new CreateBoardUseCaseInput();
//        CreateBoardUseCaseOutputImpl output = new CreateBoardUseCaseOutputImpl();
        CreateBoardUseCasePresenter presenter = new CreateBoardUseCasePresenter();

        input.setBoardName(request.getParameter("boardName"));

        createBoardUseCase.execute(input, presenter);

        ViewModel viewModel = presenter.createView();

        System.out.println(boardRepository.toString());

        request.setAttribute("createBoard", viewModel);
        request.getRequestDispatcher("WEB-INF/view/board.jsp").forward(request, response);
    }
}
