package domain.adapters.controller.team;

import domain.usecase.team.commit.CommitBoardInput;

public class CommitBoardInputImpl implements CommitBoardInput {
    private String boardId;
    private String teamId;

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamId() {
        return teamId;
    }
}
