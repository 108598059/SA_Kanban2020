package ddd.kanban.usecase.workflow.move;

import ddd.kanban.adapter.repository.board.InMemoryBoardRepository;
import ddd.kanban.adapter.repository.card.InMemoryCardRepository;
import ddd.kanban.adapter.repository.flowevent.InMemoryFlowEventRepository;
import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.workflow.Lane;
import ddd.kanban.domain.model.workflow.Workflow;
import ddd.kanban.usecase.HierarchyInitial;
import ddd.kanban.usecase.card.create.CreateCardInput;
import ddd.kanban.usecase.card.create.CreateCardOutput;
import ddd.kanban.usecase.card.create.CreateCardUseCase;
import ddd.kanban.usecase.handler.DomainEventHandler;
import ddd.kanban.usecase.handler.FlowEventHandler;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.repository.CardRepository;
import ddd.kanban.usecase.repository.FlowEventRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.workflow.mapper.WorkflowEntityMapper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoveCardUseCaseTest {

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
        domainEventBus.register(new FlowEventHandler(flowEventRepository));

        hierarchyInitial = new HierarchyInitial(boardRepository, workflowRepository, domainEventBus);
        this.boardId = hierarchyInitial.CreateBoard();
        this.workflowId = hierarchyInitial.CreateWorkflow(this.boardId);
        this.columnId = hierarchyInitial.CreateColumn(this.workflowId);
        this.defaultColumnId = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findById(this.workflowId)).getColumns().get(0).getId();
    }

    @Test
    public void testMoveCardFromDefaultColumnToColumn1(){
        String cardId = this.createCardToDefaultLane();
        Workflow workflow = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findById(this.workflowId));
        Lane defaultColunn = workflow.findColumnById(this.defaultColumnId);
        Lane column1 = workflow.findColumnById(this.columnId);

        assertEquals(1, defaultColunn.getCommittedCards().size());
        assertEquals(0, column1.getCommittedCards().size());

        MoveCardUseCase moveCardUseCase = new MoveCardUseCase(workflowRepository, domainEventBus);
        MoveCardInput moveCardInput = new MoveCardInput(workflowId, this.defaultColumnId, this.columnId, cardId);
        MoveCardOutput moveCardOutput = new MoveCardOutput();

        moveCardUseCase.execute(moveCardInput, moveCardOutput);

        assertEquals(cardId, moveCardOutput.getCardId());

        assertEquals(0, defaultColunn.getCommittedCards().size());
        assertEquals(1, column1.getCommittedCards().size());
    }

    private String createCardToDefaultLane(){
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, this.domainEventBus);
        CreateCardInput createCardInput = new CreateCardInput("Test", this.boardId, this.workflowId, this.defaultColumnId);
        CreateCardOutput createCardOutput = new CreateCardOutput();

        createCardUseCase.execute(createCardInput, createCardOutput);
        return createCardOutput.getCardId();
    }
}
