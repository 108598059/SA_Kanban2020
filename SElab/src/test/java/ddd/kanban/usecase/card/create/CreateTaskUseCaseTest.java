package ddd.kanban.usecase.card.create;

import ddd.kanban.adapter.repository.board.InMemoryBoardRepository;
import ddd.kanban.adapter.repository.card.InMemoryCardRepository;
import ddd.kanban.adapter.repository.flowevent.InMemoryFlowEventRepository;
import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.card.card.Card;
import ddd.kanban.domain.model.card.card.Task;
import ddd.kanban.usecase.HierarchyInitial;
import ddd.kanban.usecase.card.mapper.CardEntityMapper;
import ddd.kanban.usecase.domainevent.handler.DomainEventHandler;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.repository.CardRepository;
import ddd.kanban.usecase.repository.FlowEventRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.kanbanboard.workflow.mapper.WorkflowEntityMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

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
    public void setUp() {
        workflowRepository = new InMemoryWorkflowRepository();
        boardRepository = new InMemoryBoardRepository();
        cardRepository = new InMemoryCardRepository();
        flowEventRepository = new InMemoryFlowEventRepository();

        domainEventBus = new DomainEventBus();
        domainEventBus.register(new DomainEventHandler(workflowRepository, boardRepository, domainEventBus));

        hierarchyInitial = new HierarchyInitial(boardRepository, workflowRepository, domainEventBus);
        boardId = hierarchyInitial.CreateBoard();
        workflowId = hierarchyInitial.CreateWorkflow(boardId);
        columnId = hierarchyInitial.CreateColumn(workflowId);
        defaultColumnId = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findById(workflowId)).getColumns().get(0).getId();
    }

    @Test
    public void testCreateTask() {
        String cardId = createCard();

        CreateTaskUseCase createTaskUseCase = new CreateTaskUseCase(cardRepository, domainEventBus);
        CreateTaskInput createTaskInput = new CreateTaskInput("Test Task", cardId);
        CreateTaskOutput createTaskOutput = new CreateTaskOutput();

        createTaskUseCase.execute(createTaskInput, createTaskOutput);

        Card card = CardEntityMapper.mappingCardFrom(cardRepository.findById(cardId));
        Task task = card.findTaskById(createTaskOutput.getTaskId());

        assertEquals(createTaskOutput.getTaskId(), task.getId());
        assertEquals("Test Task", task.getTitle());

        List<Task> tasks = card.getTasks();
        assertEquals(1, tasks.size());
        assertEquals(tasks.get(0), task);
    }

    private String createCard() {
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, domainEventBus);
        CreateCardInput createCardInput = new CreateCardInput("Test", this.boardId, this.workflowId, this.defaultColumnId);
        CreateCardOutput createCardOutput = new CreateCardOutput();

        createCardUseCase.execute(createCardInput, createCardOutput);

        return createCardOutput.getCardId();
    }

}
