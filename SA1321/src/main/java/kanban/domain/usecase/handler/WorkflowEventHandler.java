package kanban.domain.usecase.handler;

import com.google.common.eventbus.Subscribe;
import kanban.domain.adapter.presenter.workflow.commit.CommitWorkflowPresenter;
import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.workflow.event.WorkflowCreated;
import kanban.domain.usecase.board.repository.IBoardRepository;
import kanban.domain.usecase.workflow.commit.CommitWorkflowInput;
import kanban.domain.usecase.workflow.commit.CommitWorkflowOutput;
import kanban.domain.usecase.workflow.commit.CommitWorkflowUseCase;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;

public class WorkflowEventHandler {

    private IBoardRepository boardRepository;
    private DomainEventBus eventBus;

    public WorkflowEventHandler(IBoardRepository boardRepository,
                              DomainEventBus eventBus) {
        this.boardRepository = boardRepository;
        this.eventBus = eventBus;
    }

    @Subscribe
    public void handleEvent(WorkflowCreated workflowCreated) {
        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository, eventBus);
        CommitWorkflowInput commitWorkflowInput = commitWorkflowUseCase;
        CommitWorkflowOutput commitWorkflowOutput = new CommitWorkflowPresenter();

        commitWorkflowInput.setBoardId(workflowCreated.getBoardId());
        commitWorkflowInput.setWorkflowId(workflowCreated.getWorkflowId());

        commitWorkflowUseCase.execute(commitWorkflowInput, commitWorkflowOutput);
    }

}
