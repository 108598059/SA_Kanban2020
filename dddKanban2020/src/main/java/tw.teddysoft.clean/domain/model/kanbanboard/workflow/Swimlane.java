package tw.teddysoft.clean.domain.model.kanbanboard.workflow;

import tw.teddysoft.clean.domain.model.kanbanboard.workflow.event.SwimlaneCreated;

public class Swimlane extends Lane {

    Swimlane(String title, String workflowId) {
        super(title, workflowId, LaneOrientation.HORIZONTAL);
    }

    Swimlane(String title, String workflowId, int wipLimit) {
        super(title, workflowId, LaneOrientation.HORIZONTAL, wipLimit);
    }
}
