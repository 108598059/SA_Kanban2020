package ddd.kanban.usecase.domainevent.handler;

import com.google.common.eventbus.Subscribe;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.kanbanboard.board.event.BoardCreated;
import ddd.kanban.domain.model.card.card.event.CardCreated;
import ddd.kanban.domain.model.kanbanboard.workflow.Column;
import ddd.kanban.domain.model.kanbanboard.workflow.Workflow;
import ddd.kanban.domain.model.kanbanboard.workflow.event.WorkflowCreated;
import ddd.kanban.usecase.kanbanboard.workflow.commit.CommitWorkflowInput;
import ddd.kanban.usecase.kanbanboard.workflow.commit.CommitWorkflowOutput;
import ddd.kanban.usecase.kanbanboard.workflow.commit.CommitWorkflowUseCase;
import ddd.kanban.usecase.kanbanboard.workflow.mapper.WorkflowEntityMapper;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.card.commit.CommitCardInput;
import ddd.kanban.usecase.card.commit.CommitCardOutput;
import ddd.kanban.usecase.card.commit.CommitCardUseCase;
import ddd.kanban.usecase.kanbanboard.workflow.create.CreateWorkflowInput;
import ddd.kanban.usecase.kanbanboard.workflow.create.CreateWorkflowOutput;
import ddd.kanban.usecase.kanbanboard.workflow.create.CreateWorkflowUseCase;

public class DomainEventHandler {

    private WorkflowRepository workflowRepository;
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public DomainEventHandler(WorkflowRepository workflowRepository, BoardRepository boardRepository, DomainEventBus domainEventBus){
        this.workflowRepository = workflowRepository;
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Subscribe
    public void handleDomainEvent(WorkflowCreated workflowCreated){
        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository, domainEventBus);
        CommitWorkflowInput commitWorkflowInput = new CommitWorkflowInput(workflowCreated.getBoardId(), workflowCreated.getSourceId());
        CommitWorkflowOutput commitWorkflowOutput = new CommitWorkflowOutput();

        commitWorkflowUseCase.execute(commitWorkflowInput, commitWorkflowOutput);
    }

    @Subscribe
    public void handleDomainEvent(BoardCreated boardCreated){
        final String DEFAULT_WORKFLOW_TITLE = "Default workflow";
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, domainEventBus);
        CreateWorkflowInput createWorkflowInput = new CreateWorkflowInput(DEFAULT_WORKFLOW_TITLE, boardCreated.getSourceId());
        CreateWorkflowOutput createWorkflowOutput = new CreateWorkflowOutput();

        createWorkflowUseCase.execute(createWorkflowInput, createWorkflowOutput);

    }

    @Subscribe
    public void handleDomainEvent(CardCreated cardCreated){
        Workflow workflow = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findById(cardCreated.getWorkflowId()));

        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository,domainEventBus);
        CommitCardInput commitCardInput = new CommitCardInput(cardCreated.getSourceId(), cardCreated.getWorkflowId(), cardCreated.getColumnId());
        CommitCardOutput commitCardOutput = new CommitCardOutput();

        commitCardUseCase.execute(commitCardInput, commitCardOutput);
    }
}
