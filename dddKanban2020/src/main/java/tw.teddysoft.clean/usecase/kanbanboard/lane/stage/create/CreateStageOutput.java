package tw.teddysoft.clean.usecase.kanbanboard.lane.stage.create;

import tw.teddysoft.clean.domain.usecase.Output;

public interface CreateStageOutput extends Output {

    String getId();
    void setId(String id);

}
