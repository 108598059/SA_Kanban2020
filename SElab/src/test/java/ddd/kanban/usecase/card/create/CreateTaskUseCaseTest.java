package ddd.kanban.usecase.card.create;

import ddd.kanban.adapter.repository.board.InMemoryBoardRepository;
import ddd.kanban.adapter.repository.card.InMemoryCardRepository;
import ddd.kanban.adapter.repository.flowevent.InMemoryFlowEventRepository;
import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.card.Card;
import ddd.kanban.domain.model.card.CardType;
import ddd.kanban.domain.model.card.Task;
import ddd.kanban.usecase.HierarchyInitial;
import ddd.kanban.usecase.handler.DomainEventHandler;
import ddd.kanban.usecase.handler.FlowEventHandler;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.repository.CardRepository;
import ddd.kanban.usecase.repository.FlowEventRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.workflow.mapper.WorkflowEntityMapper;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class CreateTaskUseCaseTest {
    private WorkflowRepository workflowRepository;
    private HierarchyInitial hierarchyInitial;
    private BoardRepository boardRepository;
    private FlowEventRepository flowEventRepository;
    private DomainEventBus domainEventBus;
    private String columnId;
    private String defaultColumnId;
    private String workflowId;
    private String boardId;
    private CardRepository cardRepository;

    @Before
    public void setUp(){
        workflowRepository = new InMemoryWorkflowRepository();
        boardRepository = new InMemoryBoardRepository();
        cardRepository = new InMemoryCardRepository();
        flowEventRepository = new InMemoryFlowEventRepository();

        this.domainEventBus = new DomainEventBus();
        domainEventBus.register(new DomainEventHandler(workflowRepository, boardRepository, this.domainEventBus));

        hierarchyInitial = new HierarchyInitial(boardRepository, workflowRepository, domainEventBus);
        this.boardId = hierarchyInitial.CreateBoard();
        this.workflowId = hierarchyInitial.CreateWorkflow(this.boardId);
        this.columnId = hierarchyInitial.CreateColumn(this.workflowId);
        this.defaultColumnId = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findById(this.workflowId)).getColumns().get(0).getId();
    }

    private String createCard(){
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, this.domainEventBus);
        CreateCardInput createCardInput = new CreateCardInput("Test", this.boardId, this.workflowId, this.defaultColumnId);
        CreateCardOutput createCardOutput = new CreateCardOutput();

        createCardUseCase.execute(createCardInput, createCardOutput);
        return createCardOutput.getCardId();
    }

    @Test
    public void testCreateTask(){
        String cardId = this.createCard();

        CreateTaskUseCase createTaskUseCase = new CreateTaskUseCase(cardRepository);
        CreateTaskInput createTaskInput = new CreateTaskInput("Test Task", cardId);
        CreateTaskOutput createTaskOutput = new CreateTaskOutput();

        createTaskUseCase.execute(createTaskInput, createTaskOutput);

        Card card = cardRepository.findById(cardId);
        Task task = card.findTaskById(createTaskOutput.getTaskId());

        assertEquals(createTaskOutput.getTaskId(), task.getId());
        assertEquals("Test Task", task.getTitle());
    }

}
