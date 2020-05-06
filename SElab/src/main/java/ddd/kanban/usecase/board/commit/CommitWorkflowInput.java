package ddd.kanban.usecase.board.commit;

public class CommitWorkflowInput {
    private String boardId;
    private String workflowId;

    public CommitWorkflowInput(String boardId, String workflowId) {
        this.boardId = boardId;
        this.workflowId = workflowId;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }
}
