package domain.model.aggregate.workflow.event;

public class StageCreated extends LaneCreated {
    public StageCreated(String boardId, String workflowId, String laneId) {
        super(boardId, workflowId, workflowId);
    }
}
