package domain.aggregate.workflow;


public class Stage extends Lane {

    public Stage(String name, String workflowId) {
        super(name, workflowId, LaneDirection.VERTICAL);
    }
}
