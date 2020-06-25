package tw.teddysoft.clean.domain.model.kanbanboard.workflow.event;

import tw.teddysoft.clean.domain.model.Entity;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.Swimlane;

public class SwimlaneCreated extends LaneCreated {

    public SwimlaneCreated(String id,
                           String name,
                           String workflowId) {
        super(id, name, workflowId);
    }

    public SwimlaneCreated(Entity entity) {
        super(entity);
    }

    @Override
    public Swimlane getEntity(){
        return (Swimlane) super.getEntity();
    }
}
