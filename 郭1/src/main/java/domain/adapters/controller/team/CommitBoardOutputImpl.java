package domain.adapters.controller.team;

import domain.usecase.team.commit.CommitBoardOutput;

public class CommitBoardOutputImpl implements CommitBoardOutput {
    private String boardId;

    public void setBoardId(String id) {
        this.boardId = boardId;
    }

    public String getBoardId() {
        return boardId;
    }
}
