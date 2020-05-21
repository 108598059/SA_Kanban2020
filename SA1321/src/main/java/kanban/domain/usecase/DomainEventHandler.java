package kanban.domain.usecase;

import com.google.common.eventbus.Subscribe;
import kanban.domain.adapter.presenter.workflow.commit.CommitWorkflowPresenter;
import kanban.domain.adapter.presenter.card.commit.CommitCardPresenter;
import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.card.event.CardCreated;
import kanban.domain.model.aggregate.workflow.event.WorkflowCreated;
import kanban.domain.usecase.board.repository.IBoardRepository;
import kanban.domain.usecase.card.commit.CommitCardInput;
import kanban.domain.usecase.card.commit.CommitCardOutput;
import kanban.domain.usecase.card.commit.CommitCardUseCase;
import kanban.domain.usecase.workflow.commit.CommitWorkflowInput;
import kanban.domain.usecase.workflow.commit.CommitWorkflowOutput;
import kanban.domain.usecase.workflow.commit.CommitWorkflowUseCase;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;

public class DomainEventHandler {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public DomainEventHandler(IBoardRepository boardRepository,
                              IWorkflowRepository workflowRepository,
                              DomainEventBus eventBus) {
        this.boardRepository = boardRepository;
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }

    @Subscribe
    public void handleEvent(WorkflowCreated workflowCreated) {
        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository, eventBus);
        CommitWorkflowInput commitWorkflowInput = commitWorkflowUseCase;
        CommitWorkflowOutput commitWorkflowOutput = new CommitWorkflowPresenter();

        commitWorkflowInput.setBoardId(workflowCreated.getBoardId());
        commitWorkflowInput.setWorkflowId("workflowId");

        commitWorkflowUseCase.execute(commitWorkflowInput, commitWorkflowOutput);
    }

    @Subscribe
    public void handleEvent(CardCreated cardCreated) {
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository, eventBus);
        CommitCardInput commitCardInput = new CommitCardInput();
        commitCardInput.setCardId(cardCreated.getCardId());
        commitCardInput.setStageId(cardCreated.getStageId());
        commitCardInput.setWorkflowId(cardCreated.getWorkflowId());
        CommitCardPresenter commitCardOutput = new CommitCardPresenter();

        commitCardUseCase.execute(commitCardInput, commitCardOutput);
    }


}
