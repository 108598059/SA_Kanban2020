package kanban.domain.usecase.domainEvent;

import kanban.domain.Utility;
import kanban.domain.adapter.repository.board.InMemoryBoardRepository;
import kanban.domain.adapter.repository.domainEvent.InMemoryDomainEventRepository;
import kanban.domain.adapter.repository.workflow.InMemoryWorkflowRepository;
import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.board.event.BoardCreated;
import kanban.domain.model.aggregate.board.event.WorkflowCommitted;
import kanban.domain.model.aggregate.card.event.CardCreated;
import kanban.domain.model.aggregate.workflow.event.CardCommitted;
import kanban.domain.model.aggregate.workflow.event.CardUnCommitted;
import kanban.domain.model.aggregate.workflow.event.StageCreated;
import kanban.domain.model.aggregate.workflow.event.WorkflowCreated;
import kanban.domain.model.common.DateProvider;
import kanban.domain.usecase.board.repository.IBoardRepository;
import kanban.domain.usecase.domainEvent.repository.IDomainEventRepository;
import kanban.domain.usecase.handler.CardEventHandler;
import kanban.domain.usecase.handler.DomainEventHandler;
import kanban.domain.usecase.handler.WorkflowEventHandler;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DomainEventTest {
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private IDomainEventRepository domainEventRepository;

    private Utility utility;
    private DomainEventBus eventBus;

    private String boardId;
    private String workflowId;
    private String stageId;
    private String cardId;

    @Before
    public void setUp() {

        boardRepository = new InMemoryBoardRepository();
        workflowRepository = new InMemoryWorkflowRepository();
        domainEventRepository = new InMemoryDomainEventRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new WorkflowEventHandler(boardRepository, eventBus));
        eventBus.register(new CardEventHandler(boardRepository, workflowRepository, eventBus));
        eventBus.register(new DomainEventHandler(domainEventRepository));

         utility = new Utility(
                boardRepository,
                workflowRepository,
                eventBus
        );
    }

    @Test
    public void Create_Board_Should_Save_BoardCreated_Event_In_DomainEventRepository() throws ParseException {
        String boardName = "boardName";
        DateProvider.setDate("2020/05/22");
        boardId = utility.createBoard(boardName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");


        assertEquals(1, domainEventRepository.getAll().size());
        assertEquals(boardId, ((BoardCreated)domainEventRepository.getAll().get(0)).getBoardId());
        assertEquals(boardName, ((BoardCreated)domainEventRepository.getAll().get(0)).getBoardName());
        assertEquals("2020/05/22", sdf.format((domainEventRepository.getAll().get(0)).getOccurredOn()));
    }

    @Test
    public void Create_Workflow_Should_Save_WorkflowCreated_And_WorkflowCommitted_Event_In_DomainEventRepository() throws ParseException {
        Create_Board_Should_Save_BoardCreated_Event_In_DomainEventRepository();
        String workflowName = "workflowName";
        DateProvider.setDate("2020/05/23");
        workflowId = utility.createWorkflow(boardId, workflowName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        assertEquals(3, domainEventRepository.getAll().size());
        assertEquals(boardId, ((WorkflowCreated)domainEventRepository.getAll().get(1)).getBoardId());
        assertEquals(workflowName, ((WorkflowCreated)domainEventRepository.getAll().get(1)).getName());
        assertEquals(workflowId, ((WorkflowCreated)domainEventRepository.getAll().get(1)).getWorkflowId());
        assertEquals("2020/05/23", sdf.format((domainEventRepository.getAll().get(1)).getOccurredOn()));

        assertEquals(boardId, ((WorkflowCommitted)domainEventRepository.getAll().get(2)).getBoardId());
        assertEquals(workflowId, ((WorkflowCommitted)domainEventRepository.getAll().get(2)).getWorkflowId());
        assertEquals("2020/05/23", sdf.format((domainEventRepository.getAll().get(2)).getOccurredOn()));
    }

    @Test
    public void Create_Stage_Should_Save_StageCreated_Event_In_DomainEventRepository() throws ParseException {
        Create_Workflow_Should_Save_WorkflowCreated_And_WorkflowCommitted_Event_In_DomainEventRepository();
        String stageName = "stageName";
        DateProvider.setDate("2020/05/24");
        stageId = utility.createStage(workflowId, stageName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");


        assertEquals(4, domainEventRepository.getAll().size());
        assertEquals(workflowId, ((StageCreated)domainEventRepository.getAll().get(3)).getWorkflowId());
        assertEquals(stageId, ((StageCreated)domainEventRepository.getAll().get(3)).getStageId());
        assertEquals(stageName, ((StageCreated)domainEventRepository.getAll().get(3)).getName());
        assertEquals("2020/05/24", sdf.format((domainEventRepository.getAll().get(3)).getOccurredOn()));
    }


    @Test
    public void Create_Card_Should_Save_CardCreated_And_CardCommitted_Event_In_DomainEventRepository() throws ParseException {
        boardId = utility.createBoard("boardName");
        workflowId = utility.createWorkflow(boardId, "workflowName");
        String todoStageId = utility.createStage(workflowId, "todo");
        String doingStageId = utility.createStage(workflowId, "doing");
        String cardId = utility.createCard(workflowId, todoStageId, "cardName", "description", "general", "xl");


        DateProvider.setDate("2020/05/26");
        cardId = utility.moveCard(workflowId , cardId, todoStageId, doingStageId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        assertEquals(9, domainEventRepository.getAll().size());

        assertEquals(todoStageId, ((CardUnCommitted)domainEventRepository.getAll().get(7)).getStageId());
        assertEquals(cardId, ((CardUnCommitted)domainEventRepository.getAll().get(7)).getCardId());
        assertEquals("2020/05/26", sdf.format((domainEventRepository.getAll().get(7)).getOccurredOn()));

        assertEquals(doingStageId, ((CardCommitted)domainEventRepository.getAll().get(8)).getStageId());
        assertEquals(cardId, ((CardCommitted)domainEventRepository.getAll().get(8)).getCardId());
        assertEquals("2020/05/26", sdf.format((domainEventRepository.getAll().get(8)).getOccurredOn()));
    }

    @Test
    public void Move_Card_Should_Save_CardUnCommitted_And_CardCommitted_Event_In_DomainEventRepository() throws ParseException {
        Create_Stage_Should_Save_StageCreated_Event_In_DomainEventRepository();
        String cardName = "cardName";
        String description = "description";
        String type = "general";
        String size = "xxl";
        DateProvider.setDate("2020/05/25");
        cardId = utility.createCard(workflowId, stageId, cardName, description, type, size);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        assertEquals(6, domainEventRepository.getAll().size());
        assertEquals(workflowId, ((CardCreated)domainEventRepository.getAll().get(4)).getWorkflowId());
        assertEquals(stageId, ((CardCreated)domainEventRepository.getAll().get(4)).getStageId());
        assertEquals(cardId, ((CardCreated)domainEventRepository.getAll().get(4)).getCardId());
        assertEquals(cardName, ((CardCreated)domainEventRepository.getAll().get(4)).getName());
        assertEquals(description, ((CardCreated)domainEventRepository.getAll().get(4)).getDescription());
        assertEquals(type, ((CardCreated)domainEventRepository.getAll().get(4)).getType());
        assertEquals(size, ((CardCreated)domainEventRepository.getAll().get(4)).getSize());
        assertEquals("2020/05/25", sdf.format((domainEventRepository.getAll().get(4)).getOccurredOn()));

        assertEquals(stageId, ((CardCommitted)domainEventRepository.getAll().get(5)).getStageId());
        assertEquals(cardId, ((CardCommitted)domainEventRepository.getAll().get(5)).getCardId());
        assertEquals("2020/05/25", sdf.format((domainEventRepository.getAll().get(5)).getOccurredOn()));
    }
}
