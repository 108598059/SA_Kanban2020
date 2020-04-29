package domain.aggregate.workflow;


public class Swimlane extends Lane{

    public Swimlane(String name, String workflowId) {
        super(name, workflowId, LaneDirection.HORIZONTAL);
    }
}
