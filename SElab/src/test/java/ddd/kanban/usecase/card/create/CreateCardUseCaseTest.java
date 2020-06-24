package ddd.kanban.usecase.card.create;

import ddd.kanban.adapter.repository.board.InMemoryBoardRepository;
import ddd.kanban.adapter.repository.card.InMemoryCardRepository;
import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.card.Card;
import ddd.kanban.domain.model.kanbanboard.workflow.Lane;
import ddd.kanban.domain.model.kanbanboard.workflow.Workflow;
import ddd.kanban.usecase.HierarchyInitial;
import ddd.kanban.usecase.card.mapper.CardEntityMapper;
import ddd.kanban.usecase.domainevent.handler.DomainEventHandler;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.repository.CardRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.kanbanboard.workflow.mapper.WorkflowEntityMapper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateCardUseCaseTest {
    private CardRepository cardRepository;
    private HierarchyInitial hierarchyInitial;
    private String boardId;
    private String workflowId;
    private String columnId;
    private DomainEventBus domainEventBus;
    private WorkflowRepository workflowRepository;
    private BoardRepository boardRepository;

    @Before
    public void setUp(){
        cardRepository = new InMemoryCardRepository();

        this.workflowRepository = new InMemoryWorkflowRepository();
        this.boardRepository = new InMemoryBoardRepository();
        this.domainEventBus = new DomainEventBus();
        hierarchyInitial = new HierarchyInitial(boardRepository, workflowRepository, domainEventBus);
        this.boardId = hierarchyInitial.CreateBoard();
        this.workflowId = hierarchyInitial.CreateWorkflow(this.boardId);
        this.columnId = hierarchyInitial.CreateColumn(this.workflowId);
        domainEventBus.register(new DomainEventHandler(this.workflowRepository, boardRepository, domainEventBus));
    }

    @Test
    public void testCreateCardShouldCommitToTheLane(){
        String cardTitle = "TestCard";

        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, domainEventBus);
        CreateCardInput createCardInput = new CreateCardInput(cardTitle, this.boardId, this.workflowId, this.columnId);
        CreateCardOutput createCardOutput = new CreateCardOutput();

        createCardUseCase.execute(createCardInput, createCardOutput);

        Card card = CardEntityMapper.mappingCardFrom(cardRepository.findById(createCardOutput.getCardId()));

        assertEquals(cardTitle, card.getTitle());
        assertEquals(createCardOutput.getCardId(), card.getId());

        Workflow workflow = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findById(this.workflowId));
        Lane column = workflow.findColumnById(this.columnId);

        assertEquals(1, column.getCommittedCards().size());
    }
}
