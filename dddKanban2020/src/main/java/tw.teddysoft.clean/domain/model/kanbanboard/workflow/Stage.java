package tw.teddysoft.clean.domain.model.kanbanboard.workflow;

public class Stage extends Lane {

    Stage(String name, String workflowId) {
        super(name, workflowId, LaneOrientation.VERTICAL);
//        publishDomainEvent();
    }

    Stage(String name, String workflowId, int wipLimit) {
        super(name, workflowId, LaneOrientation.VERTICAL, wipLimit);
//        publishDomainEvent();
    }

//    private void publishDomainEvent(){
//        DomainEventPublisher
//                .instance()
//                .publish(new StageCreated(
//                        this.getId(),
//                        this.getName(),
//                        this.getWorkflowId()));
//    }
}
