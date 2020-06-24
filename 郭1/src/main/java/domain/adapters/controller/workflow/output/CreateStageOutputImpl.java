package domain.adapters.controller.workflow.output;

import domain.usecase.workflow.create.CreateStageOutput;

public class CreateStageOutputImpl implements CreateStageOutput {
    private String _id ;

    public void setStageId( String id ) {
        this._id = id ;
    }
    public String getStageId() {
        return this._id ;
    }
}
