package domain.entity.aggregate.workflow.event;

import domain.entity.DomainEvent;

public class SwimlaneCreated implements DomainEvent {
    private String workflowId ;
    private String stageId ;
    private String swimlaneId ;
    private String swimlaneName ;

    public SwimlaneCreated( String workflowId, String stageId, String swimlaneId, String swimlaneName ) {
        this.workflowId = workflowId ;
        this.stageId = stageId ;
        this.swimlaneId = swimlaneId ;
        this.swimlaneName = swimlaneName ;

    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getStageId() {
        return stageId;
    }

    public String getSwimlaneId() {
        return swimlaneId;
    }

    public String getSwimlaneName() {
        return swimlaneName;
    }
}
