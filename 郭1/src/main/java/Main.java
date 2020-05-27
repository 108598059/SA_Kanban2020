import domain.adapters.controller.board.BoardController;
import domain.adapters.presenter.BoardPresenter;
import domain.adapters.repository.BoardRepositoryImpl;
import domain.entity.DomainEventBus;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.view.MainFrame;
import domain.adapters.View;


public class Main {

    public static void main(String[] args) {

        DomainEventBus eventBus = new DomainEventBus();
        BoardPresenter presenter = new BoardPresenter();
        BoardController boardController = new BoardController(new CreateBoardUseCase(new BoardRepositoryImpl(), eventBus), presenter);
        View mainframe = new MainFrame(boardController);

    }
}
