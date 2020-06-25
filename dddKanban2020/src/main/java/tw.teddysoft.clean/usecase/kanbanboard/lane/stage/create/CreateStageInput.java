package tw.teddysoft.clean.usecase.kanbanboard.lane.stage.create;

import tw.teddysoft.clean.domain.usecase.Input;


public interface CreateStageInput extends Input {

    void setWorkflowId(String workflowId);
    String getWorkflowId();

    void setName(String name);
    String getName();

    void setParentId(String parentId);
    String getParentId();

    boolean isTopStage();
}