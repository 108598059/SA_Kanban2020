package tw.teddysoft.clean.domain.model.kanbanboard.workflow.event;

import tw.teddysoft.clean.domain.model.Entity;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.Lane;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.LaneOrientation;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.Stage;

public class StageCreated extends LaneCreated {

    public StageCreated(String id,
                        String name,
                        String workflowId) {
        super(id, name, workflowId);
    }

    public StageCreated(Entity entity) {
        super(entity);
    }

    @Override
    public Stage getEntity(){
        return (Stage) super.getEntity();
    }
}
