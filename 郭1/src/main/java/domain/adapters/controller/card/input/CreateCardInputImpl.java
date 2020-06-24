package domain.adapters.controller.card.input;

import domain.usecase.card.create.CreateCardInput;

public class CreateCardInputImpl implements CreateCardInput {

    private String _name ;
    private String workflowId;
    private String stageId;
    private String swimlaneId;

    public void setCardName(String name) {
        this._name = name ;
    }

    public String getCardName() {
        return this._name ;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowid) {
        this.workflowId = workflowid;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public String getSwimlaneId() {
        return swimlaneId;
    }

    public void setSwimlaneId(String swimlaneId) {
        this.swimlaneId = swimlaneId;
    }


}
