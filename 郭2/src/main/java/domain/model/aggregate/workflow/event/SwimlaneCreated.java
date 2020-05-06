package domain.model.aggregate.workflow.event;

public class SwimlaneCreated extends LaneCreated {
    public SwimlaneCreated(String boardId, String workflowId, String laneId) {
        super(boardId, workflowId, workflowId);
    }
}
