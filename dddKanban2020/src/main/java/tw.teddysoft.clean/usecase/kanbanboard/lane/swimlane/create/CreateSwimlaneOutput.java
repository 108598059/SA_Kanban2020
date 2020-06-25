package tw.teddysoft.clean.usecase.kanbanboard.lane.swimlane.create;

import tw.teddysoft.clean.domain.usecase.Output;

public interface CreateSwimlaneOutput extends Output {
    String getId();
    void setId(String laneId);
}
