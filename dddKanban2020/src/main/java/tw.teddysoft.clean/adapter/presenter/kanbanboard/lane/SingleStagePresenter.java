package tw.teddysoft.clean.adapter.presenter.kanbanboard.lane;

import tw.teddysoft.clean.usecase.kanbanboard.lane.stage.create.CreateStageOutput;
import tw.teddysoft.clean.usecase.kanbanboard.lane.swimlane.create.CreateSwimlaneOutput;

public class SingleStagePresenter implements
        CreateStageOutput,
        CreateSwimlaneOutput
{

    private String id;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String laneId) {
        this.id = laneId;
    }
}
