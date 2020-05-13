package domain.adapters.controller.workflow;

import domain.usecase.swimlane.create.CreateSwimlaneOutput;

public class CreateSwimlaneOutputImpl implements CreateSwimlaneOutput {

    private String swimlaneId;

    public String getSwimlaneId() {
        return swimlaneId;
    }

    public void setSwimlaneId(String id) {
        this.swimlaneId = id;
    }
}
