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
public class KanbanBoard extends HttpServlet {

    @Inject
    private IBoardRepository boardRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(boardRepository.toString());
    }
}
