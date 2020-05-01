package ddd.kanban.usecase;

import com.google.common.eventbus.Subscribe;
import ddd.kanban.domain.model.board.event.BoardCreated;
import ddd.kanban.domain.model.card.event.CardCreated;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.workflow.commit.CommitCardInput;
import ddd.kanban.usecase.workflow.commit.CommitCardOutput;
import ddd.kanban.usecase.workflow.commit.CommitCardUseCase;
import ddd.kanban.usecase.workflow.create.CreateWorkflowInput;
import ddd.kanban.usecase.workflow.create.CreateWorkflowOutput;
import ddd.kanban.usecase.workflow.create.CreateWorkflowUseCase;

public class DomainEventHandler {

    private WorkflowRepository workflowRepository;
    private BoardRepository boardRepository;

    public DomainEventHandler(WorkflowRepository workflowRepository){
        this.workflowRepository = workflowRepository;
    }

    @Subscribe
    public void handleDomainEvent(BoardCreated boardCreated){
        final String DEFAULT_WORKFLOW_TITLE = "default workflow";
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository);
        CreateWorkflowInput createWorkflowInput = new CreateWorkflowInput(DEFAULT_WORKFLOW_TITLE, boardCreated.getBoardId());
        CreateWorkflowOutput createWorkflowOutput = new CreateWorkflowOutput();

        createWorkflowUseCase.execute(createWorkflowInput, createWorkflowOutput);
    }

    @Subscribe
    public void handleDomainEvent(CardCreated cardCreated){
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository);
        CommitCardInput commitCardInput = new CommitCardInput(cardCreated.getCardId(), cardCreated.getWorkflowId(), cardCreated.getLaneId());
        CommitCardOutput commitCardOutput = new CommitCardOutput();

        commitCardUseCase.execute(commitCardInput, commitCardOutput);
    }

}
