package domain.adapters.controller.board;

import domain.usecase.board.create.CreateBoardInput;

public class CreateBoardInputImpl implements CreateBoardInput {
    private String name;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
