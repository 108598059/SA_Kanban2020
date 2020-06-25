package domain.adapter.controller;

import domain.adapter.presenter.CreateBoardUseCasePresenter;
import domain.adapter.view_model.ViewModel;
import domain.model.DomainEventBus;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.board.create.CreateBoardUseCaseInput;
import domain.usecase.board.repository.IBoardRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Homepage extends HttpServlet {
    private List<ViewModel> viewModelList = new ArrayList<>();
    private IBoardRepository boardRepository;
    private DomainEventBus eventBus;

    public Homepage(IBoardRepository boardRepository,DomainEventBus eventBus){
        this.boardRepository = boardRepository;
        this.eventBus = eventBus;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, eventBus);
        CreateBoardUseCaseInput input = new CreateBoardUseCaseInput();
//        CreateBoardUseCaseOutputImpl output = new CreateBoardUseCaseOutputImpl();
        CreateBoardUseCasePresenter presenter = new CreateBoardUseCasePresenter();//?????????????

        input.setBoardName(request.getParameter("boardName"));

        createBoardUseCase.execute(input, presenter);

        ViewModel viewModel = presenter.createViewModel();

        viewModelList.add(viewModel);

        request.setAttribute("boardList", viewModelList);
        request.getRequestDispatcher("WEB-INF/view/homepage.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("boardList", viewModelList);
        request.getRequestDispatcher("WEB-INF/view/homepage.jsp").forward(request, response);
    }
}
