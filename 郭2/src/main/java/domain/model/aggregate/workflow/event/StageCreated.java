package domain.model.aggregate.workflow.event;

import java.util.Date;

public class StageCreated extends LaneCreated {
    public StageCreated(String boardId, String workflowId, String laneId) {
        super(boardId, workflowId, workflowId);
    }
}
