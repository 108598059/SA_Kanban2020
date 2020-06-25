package ddd.kanban.usecase.card.create;

import ddd.kanban.adapter.repository.board.InMemoryBoardRepository;
import ddd.kanban.adapter.repository.card.InMemoryCardRepository;
import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.card.card.Card;
import ddd.kanban.domain.model.kanbanboard.workflow.Column;
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
    private DomainEventBus domainEventBus;
    private WorkflowRepository workflowRepository;
    private BoardRepository boardRepository;

    @Before
    public void setUp(){
        cardRepository = new InMemoryCardRepository();
        workflowRepository = new InMemoryWorkflowRepository();
        boardRepository = new InMemoryBoardRepository();
        domainEventBus = new DomainEventBus();
        hierarchyInitial = new HierarchyInitial(boardRepository, workflowRepository, domainEventBus);
        domainEventBus.register(new DomainEventHandler(workflowRepository, boardRepository, domainEventBus));
    }

    @Test
    public void testCreateCardShouldCommitToTheDefaultColumn(){
        String boardId = hierarchyInitial.CreateBoard();
        String workflowId = hierarchyInitial.CreateWorkflow(boardId);
        Workflow workflow = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findById(workflowId));
        String defaultColumnId = workflow.getColumns().get(0).getId();
        String cardTitle = "TestCard";

        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, domainEventBus);
        CreateCardInput createCardInput = new CreateCardInput(cardTitle, boardId, workflowId, defaultColumnId);
        CreateCardOutput createCardOutput = new CreateCardOutput();

        createCardUseCase.execute(createCardInput, createCardOutput);

        Card card = CardEntityMapper.mappingCardFrom(cardRepository.findById(createCardOutput.getCardId()));

        assertEquals(createCardOutput.getCardId(), card.getId());
        assertEquals(cardTitle, card.getTitle());
        assertEquals(defaultColumnId, card.getColumnId());

        Column column = workflow.findColumnById(defaultColumnId);

        assertEquals(1, column.getCommittedCards().size());
    }
}
