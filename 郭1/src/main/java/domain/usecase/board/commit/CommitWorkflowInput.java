package domain.usecase.board.commit;

public interface CommitWorkflowInput {
    String getBoardId();
    String getWorkflowId();
    void setBoardId(String boardId);
    void setWorkflowId(String workflowId);
}
