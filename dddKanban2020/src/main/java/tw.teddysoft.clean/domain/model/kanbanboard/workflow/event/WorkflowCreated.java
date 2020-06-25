package tw.teddysoft.clean.domain.model.kanbanboard.workflow.event;

import tw.teddysoft.clean.domain.model.AbstractDomainEvent;
import tw.teddysoft.clean.domain.model.DomainEvent;
import tw.teddysoft.clean.domain.model.Entity;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.Workflow;

public class WorkflowCreated extends AbstractDomainEvent {

    private String boardId;

    public WorkflowCreated(String boardId, String id, String name) {
        super(id, name);
        this.boardId = boardId;
    }

    public WorkflowCreated(Entity entity) {
        super(entity);
    }

    @Override
    public Workflow getEntity(){
        return (Workflow) super.getEntity();
    }
}
