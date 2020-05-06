package domain.controller;

import domain.usecase.board.commit.CommitWorkflowInput;

public class CommitWorkflowInputImpl implements CommitWorkflowInput {

    private String boardId;
    private String workflowId;


    public String getBoardId() {
        return boardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }
}
