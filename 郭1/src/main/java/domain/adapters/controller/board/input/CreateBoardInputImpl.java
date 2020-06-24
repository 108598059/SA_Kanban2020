package domain.adapters.controller.board.input;

import domain.usecase.board.create.CreateBoardInput;

public class CreateBoardInputImpl implements CreateBoardInput {
    private String name;
    private String teamId;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamId() {
        return teamId;
    }
}
