package domain.entity.aggregate.workflow.event;

import domain.entity.DomainEvent;

public class StageCreated implements DomainEvent {
    private String workflowId ;
    private String stageId ;
    private String stageName ;

    public StageCreated( String workflowId, String stageId, String stageName ) {
        this.stageId = stageId ;
        this.workflowId = workflowId ;
        this.stageName = stageName ;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getStageId() {
        return stageId;
    }

    public String getStageName() {
        return stageName;
    }
}
