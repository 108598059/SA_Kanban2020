package domain.usecase.workflow;

import domain.model.aggregate.workflow.Lane;

import java.util.List;

public class WorkflowDTO {
    private String boardId;
    private String workflowId;
    private String workflowName;
    private List<Lane> laneList;

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public List<Lane> getLaneList() {
        return laneList;
    }

    public void setLaneList(List<Lane> laneList) {
        this.laneList = laneList;
    }
}
