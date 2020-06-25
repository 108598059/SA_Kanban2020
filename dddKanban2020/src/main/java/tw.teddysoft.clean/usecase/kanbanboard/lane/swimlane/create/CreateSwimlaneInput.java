package tw.teddysoft.clean.usecase.kanbanboard.lane.swimlane.create;

import tw.teddysoft.clean.domain.usecase.Input;


public interface CreateSwimlaneInput extends Input {

    void setWorkflowId(String workflowId);

    void setTitle(String title);

    String getWorkflowId();

    String getTitle();

    void setParentId(String parentId);

    String getParentId();
}