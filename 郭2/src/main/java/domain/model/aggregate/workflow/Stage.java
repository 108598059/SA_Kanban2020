package domain.model.aggregate.workflow;


public class Stage extends Lane {

    public Stage(String name, String workflowId) {
        super(name, workflowId, LaneDirection.VERTICAL);
    }
}
