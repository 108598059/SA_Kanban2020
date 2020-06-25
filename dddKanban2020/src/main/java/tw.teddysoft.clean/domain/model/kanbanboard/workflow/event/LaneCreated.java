package tw.teddysoft.clean.domain.model.kanbanboard.workflow.event;

import tw.teddysoft.clean.domain.model.AbstractDomainEvent;
import tw.teddysoft.clean.domain.model.Entity;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.Lane;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.LaneOrientation;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.Workflow;

abstract public class LaneCreated extends AbstractDomainEvent {

    private String workflowId;

    public LaneCreated(String sourceId,
                       String Title,
                       String workflowId) {
        super(sourceId, Title);
        this.workflowId = workflowId;
    }

    public LaneCreated(Entity entity) {
        super(entity);
    }

    @Override
    public Lane getEntity(){
        return (Lane) super.getEntity();
    }
}
