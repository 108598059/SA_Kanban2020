package domain.adapters.controller.card;

import domain.usecase.card.create.CreateCardOutput;

public class CreateCardOutputImpl implements CreateCardOutput {
    private String _id ;
    private String workflowId;

    public void setCardId(String id) {
        this._id = id ;
    }
    public String getCardId() {
        return this._id;
    }

    public void setWorkflowId(String workflowId){this.workflowId = workflowId;}
    public String getWorkflowId(){return this.workflowId;}
}
