package domain.adapters.controller.workflow.output;

import domain.usecase.workflow.create.CreateSwimlaneOutput;

public class CreateSwimlaneOutputImpl implements CreateSwimlaneOutput {

    private String swimlaneId;

    public String getSwimlaneId() {
        return swimlaneId;
    }

    public void setSwimlaneId(String id) {
        this.swimlaneId = id;
    }
}
