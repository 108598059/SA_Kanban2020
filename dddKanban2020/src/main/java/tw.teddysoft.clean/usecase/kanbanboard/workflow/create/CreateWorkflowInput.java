package tw.teddysoft.clean.usecase.kanbanboard.workflow.create;

import tw.teddysoft.clean.domain.usecase.Input;

public interface CreateWorkflowInput extends Input {
    String getWorkflowName();
    CreateWorkflowInput setWorkflowName(String workflowName);
    String getBoardId();
    CreateWorkflowInput setBoardId(String boardId);
}
