package ddd.kanban.usecase.card.move;

import ddd.kanban.adapter.repository.board.InMemoryBoardRepository;
import ddd.kanban.adapter.repository.card.InMemoryCardRepository;
import ddd.kanban.adapter.repository.flowevent.InMemoryFlowEventRepository;
import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.card.card.Card;
import ddd.kanban.domain.model.kanbanboard.workflow.Column;
import ddd.kanban.domain.model.kanbanboard.workflow.Workflow;
import ddd.kanban.usecase.HierarchyInitial;
import ddd.kanban.usecase.card.create.CreateCardInput;
import ddd.kanban.usecase.card.create.CreateCardOutput;
import ddd.kanban.usecase.card.create.CreateCardUseCase;
import ddd.kanban.usecase.card.mapper.CardEntityMapper;
import ddd.kanban.usecase.domainevent.handler.CardEventHandler;
import ddd.kanban.usecase.domainevent.handler.DomainEventHandler;
import ddd.kanban.usecase.domainevent.handler.FlowEventHandler;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.repository.CardRepository;
import ddd.kanban.usecase.repository.FlowEventRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.kanbanboard.workflow.mapper.WorkflowEntityMapper;
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

        domainEventBus = new DomainEventBus();
        domainEventBus.register(new DomainEventHandler(workflowRepository, boardRepository, domainEventBus));
        domainEventBus.register(new FlowEventHandler(flowEventRepository));
        domainEventBus.register(new CardEventHandler(cardRepository));

        hierarchyInitial = new HierarchyInitial(boardRepository, workflowRepository, domainEventBus);
        boardId = hierarchyInitial.CreateBoard();
        workflowId = hierarchyInitial.CreateWorkflow(boardId);
        columnId = hierarchyInitial.CreateColumn(workflowId);
        defaultColumnId = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findById(workflowId)).getColumns().get(0).getId();
    }

    @Test
    public void testMoveCardFromDefaultColumnToColumn1(){
        String cardId = createCardToDefaultColumn();
        Workflow workflow = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findById(workflowId));
        Column defaultColumn = workflow.findColumnById(defaultColumnId);
        Column toColumn = workflow.findColumnById(columnId);
        Card card = CardEntityMapper.mappingCardFrom(cardRepository.findById(cardId));

        assertEquals(defaultColumnId, card.getColumnId());
        assertEquals(1, defaultColumn.getCommittedCards().size());
        assertEquals(0, toColumn.getCommittedCards().size());

        MoveCardUseCase moveCardUseCase = new MoveCardUseCase(workflowRepository, domainEventBus);
        MoveCardInput moveCardInput = new MoveCardInput(workflowId, defaultColumnId, columnId, cardId);
        MoveCardOutput moveCardOutput = new MoveCardOutput();

        moveCardUseCase.execute(moveCardInput, moveCardOutput);

        assertEquals(cardId, moveCardOutput.getCardId());
        assertEquals(0, defaultColumn.getCommittedCards().size());
        assertEquals(1, toColumn.getCommittedCards().size());

        card = CardEntityMapper.mappingCardFrom(cardRepository.findById(cardId));
        assertEquals(toColumn.getId(), card.getColumnId());
    }

    private String createCardToDefaultColumn(){
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, domainEventBus);
        CreateCardInput createCardInput = new CreateCardInput("Test", boardId, workflowId, defaultColumnId);
        CreateCardOutput createCardOutput = new CreateCardOutput();

        createCardUseCase.execute(createCardInput, createCardOutput);

        return createCardOutput.getCardId();
    }
}
