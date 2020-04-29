package ddd.kanban.usecase;

import com.google.common.eventbus.Subscribe;
import ddd.kanban.domain.model.card.event.CardCreated;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.workflow.commit.CommitCardInput;
import ddd.kanban.usecase.workflow.commit.CommitCardOutput;
import ddd.kanban.usecase.workflow.commit.CommitCardUseCase;

public class DomainEventHandler {

    private WorkflowRepository workflowRepository;

    public DomainEventHandler(WorkflowRepository workflowRepository){
        this.workflowRepository = workflowRepository;
    }

    @Subscribe
    public void handleDomainEvent(CardCreated cardCreated){
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository);
        CommitCardInput commitCardInput = new CommitCardInput(cardCreated.getCardId(), cardCreated.getWorkflowId(), cardCreated.getLaneId());
        CommitCardOutput commitCardOutput = new CommitCardOutput();

        commitCardUseCase.execute(commitCardInput, commitCardOutput);
    }

}
