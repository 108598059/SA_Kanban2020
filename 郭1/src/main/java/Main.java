import domain.adapters.controller.board.BoardController;
import domain.adapters.presenter.BoardPresenter;
import domain.adapters.repository.BoardRepositoryImpl;
import domain.adapters.viewmodel.board.BoardViewModel;
import domain.entity.board.Board;
import domain.usecase.board.BoardRepository;
import domain.view.MainFrame;
import domain.adapters.View;


public class Main {

    public static void main(String[] args) {

        BoardPresenter presenter = new BoardPresenter();
        BoardController boardController = new BoardController(new BoardRepositoryImpl(), presenter);
        View mainframe = new MainFrame(boardController, presenter.createBoardViewModel());

    }
}
