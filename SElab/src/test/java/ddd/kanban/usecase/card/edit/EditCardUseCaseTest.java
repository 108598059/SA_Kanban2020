package ddd.kanban.usecase.card.edit;

import ddd.kanban.adapter.repository.board.InMemoryBoardRepository;
import ddd.kanban.adapter.repository.card.InMemoryCardRepository;
import ddd.kanban.adapter.repository.flowevent.InMemoryFlowEventRepository;
import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.card.Card;
import ddd.kanban.domain.model.card.CardType;
import ddd.kanban.usecase.HierarchyInitial;
import ddd.kanban.usecase.card.create.CreateCardInput;
import ddd.kanban.usecase.card.create.CreateCardOutput;
import ddd.kanban.usecase.card.create.CreateCardUseCase;
import ddd.kanban.usecase.card.edit.EditCardUseCase;
import ddd.kanban.usecase.card.edit.EditCardUseCaseInput;
import ddd.kanban.usecase.card.edit.EditCardUseCaseOutput;
import ddd.kanban.usecase.handler.DomainEventHandler;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.repository.CardRepository;
import ddd.kanban.usecase.repository.FlowEventRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.workflow.mapper.WorkflowEntityMapper;
import org.junit.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class EditCardUseCaseTest {
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
    public void testEditCardUseCase() {

        String newCardName = "cardNewName";
        String newCardDescription = "cardNewDescription";
        CardType newCardCardType = new CardType();
        List<String> cardTags = new ArrayList<String>();
        cardTags.add("cardNewTag1");
        cardTags.add("cardNewTag2");
        List<String> cardAssignUsers = new ArrayList<String>();
        cardAssignUsers.add("108598001");
        cardAssignUsers.add("108598099");
        Date newCardPlannedStartDate = new Date();
        Date newCardPlannedFinishDate = new Date();
        int newCardPriority = 87;

        String cardId = this.createCard();

        EditCardUseCase editCardUseCase = new EditCardUseCase(cardRepository);
        EditCardUseCaseInput editCardUseCaseInput = new EditCardUseCaseInput(this.workflowId, cardId, newCardName, newCardDescription, newCardCardType, cardTags, cardAssignUsers, newCardPlannedStartDate, newCardPlannedFinishDate, newCardPriority);
        EditCardUseCaseOutput editCardUseCaseOutput = new EditCardUseCaseOutput();

        editCardUseCase.execute(editCardUseCaseInput, editCardUseCaseOutput);

        Card card = cardRepository.findById(cardId);
        assertEquals(cardId, card.getId());
        assertEquals(newCardName, card.getTitle());
        assertEquals(newCardDescription, card.getDescription());
        assertEquals(newCardPriority, card.getPriority());
    }

}
