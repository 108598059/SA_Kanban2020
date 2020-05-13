import domain.adapters.controller.board.BoardController;
import domain.view.MainFrame;
import domain.adapters.View;


public class Main {

    public static void main(String[] args) {

        BoardController boardController = new BoardController();
        View mainframe = new MainFrame(boardController);

    }
}
