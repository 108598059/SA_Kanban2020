package domain.adapters.controller.workflow;

import domain.usecase.stage.create.CreateStageOutput;

public class CreateStageOutputImpl implements CreateStageOutput {
    private String _id ;

    public void setStageId( String id ) {
        this._id = id ;
    }
    public String getStageId() {
        return this._id ;
    }
}
