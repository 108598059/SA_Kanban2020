package domain;

import domain.adapter.repository.board.MySqlBoardRepository;
import domain.usecase.board.repository.IBoardRepository;

public class Main {
    private static Main main;
    private IBoardRepository boardRepository;

    public Main() {
        boardRepository = new MySqlBoardRepository();
    }

    public static Main getInstance() {
        if(main == null) {
            main = new Main();
        }

        return main;
    }

    public IBoardRepository getMySqlBoardRepository() {
        return this.boardRepository;
    }
}
