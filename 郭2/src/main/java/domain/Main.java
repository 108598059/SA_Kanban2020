package domain;

import domain.adapter.repository.board.MySqlBoardRepository;

public class Main {
    private static Main main;


    public Main() {}

    public static Main getInstance() {
        if(main == null) {
            main = new Main();
        }

        return main;
    }

    public MySqlBoardRepository getMySqlBoardRepository() {
        return new MySqlBoardRepository();
    }
}
