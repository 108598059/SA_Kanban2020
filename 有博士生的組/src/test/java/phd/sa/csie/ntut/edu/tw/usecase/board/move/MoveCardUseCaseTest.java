package phd.sa.csie.ntut.edu.tw.usecase.board.move;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.adapter.presenter.card.create.CreateCardPresenter;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.board.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.card.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.event.MemoryEventLogRepository;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.board.commit.card.CommitCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.column.setwip.SetColumnWIPUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.setwip.SetColumnWIPUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.column.setwip.SetColumnWIPUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.DomainEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.domain.EventSourcingHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.domain.dto.DomainEventDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.card.CardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.event.EventLogRepository;

import static org.junit.Assert.*;

public class MoveCardUseCaseTest {

    private CardRepository cardRepository;
    private BoardRepository boardRepository;
    private CreateCardUseCase createCardUseCase;
    private CreateColumnUseCase createColumnUseCase;
    private Card card;
    private Card card2;
    private String fromColumnID;
    private String toColumnID;
    private String boardID;
    private DomainEventBus eventBus;

    @Before
    public void given_a_card_and_a_board_and_two_columns() {
        this.eventBus = new DomainEventBus();
        this.cardRepository = new MemoryCardRepository();
        this.boardRepository = new MemoryBoardRepository();
        this.createCardUseCase = new CreateCardUseCase(this.eventBus, this.cardRepository, this.boardRepository);
        this.createColumnUseCase = new CreateColumnUseCase(this.eventBus, this.boardRepository);

        Board board = new Board(UUID.randomUUID(), "Kanban");
        board.createColumn("Backlog", 0);
        board.createColumn("Archive", 1);
        this.boardID = board.getID().toString();
        this.boardRepository.save(BoardDTOConverter.toDTO(board));
        this.eventBus.register(new CommitCardUseCase(this.eventBus, this.cardRepository, this.boardRepository));

        CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
        CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardPresenter();

        createCardUseCaseInput.setCardName("Implement a column");
        createCardUseCaseInput.setBoardID(this.boardID.toString());
        this.createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);
        this.card = CardDTOConverter.toEntity(this.cardRepository.findByID(createCardUseCaseOutput.getCardID()));

        createCardUseCaseInput.setCardName("Implement a board");
        this.createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);
        this.card2 = CardDTOConverter.toEntity(this.cardRepository.findByID(createCardUseCaseOutput.getCardID()));

        this.fromColumnID = board.getBacklogColumn().getID().toString();

        CreateColumnUseCaseInput createColumnUseCaseInput = new CreateColumnUseCaseInput();
        CreateColumnUseCaseOutput createColumnUseCaseOutput = new CreateColumnUseCaseOutput();

        createColumnUseCaseInput.setBoardID(this.boardID.toString());
        createColumnUseCaseInput.setTitle("develop");

        this.createColumnUseCase.execute(createColumnUseCaseInput, createColumnUseCaseOutput);

        this.toColumnID = createColumnUseCaseOutput.getID();
    }

    @Test
    public void the_to_column_should_contain_the_moved_card () {
        MoveCardUseCase moveCardUseCase = new MoveCardUseCase(this.eventBus, this.boardRepository);
        MoveCardUseCaseInput moveCardUseCaseInput = new MoveCardUseCaseInput();
        MoveCardUseCaseOutput moveCardUseCaseOutput = new MoveCardUseCaseOutput();

        moveCardUseCaseInput.setBoardID(this.boardID);
        moveCardUseCaseInput.setCardID(this.card.getID().toString());
        moveCardUseCaseInput.setFromColumnID(this.fromColumnID);
        moveCardUseCaseInput.setToColumnID(this.toColumnID);

        moveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);

        assertEquals(this.card.getID().toString(), moveCardUseCaseOutput.getCardID());
        assertEquals(this.fromColumnID, moveCardUseCaseOutput.getOldColumnID());
        assertEquals(this.toColumnID, moveCardUseCaseOutput.getNewColumnID());

        Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(this.boardID.toString()));
        assertTrue(board.findColumnByID(UUID.fromString(this.toColumnID)).cardExist(this.card.getID()));
        assertFalse(board.findColumnByID(UUID.fromString(this.fromColumnID)).cardExist(this.card.getID()));
    }

    @Test
    public void move_card_should_post_left_events_and_entered_events() {
        EventLogRepository eventLogRepository = new MemoryEventLogRepository();
        DomainEventHandler eventSourcingHandler = new EventSourcingHandler(eventLogRepository);
        this.eventBus.register(eventSourcingHandler);

        MoveCardUseCase moveCardUseCase = new MoveCardUseCase(this.eventBus, this.boardRepository);
        MoveCardUseCaseInput moveCardUseCaseInput = new MoveCardUseCaseInput();
        MoveCardUseCaseOutput moveCardUseCaseOutput = new MoveCardUseCaseOutput();

        moveCardUseCaseInput.setBoardID(this.boardID);
        moveCardUseCaseInput.setCardID(this.card.getID().toString());
        moveCardUseCaseInput.setFromColumnID(this.fromColumnID);
        moveCardUseCaseInput.setToColumnID(this.toColumnID);

        assertEquals(0, eventLogRepository.size());

        moveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);

        assertEquals(2, eventLogRepository.size());

        List<DomainEventDTO> eventList = eventLogRepository.getAll();

        assertEquals("[Card Left Column Event] Card: " + this.card.getID() + " left the column: " + this.fromColumnID, eventList.get(0).getSourceName());
        assertEquals("[Card Entered Event] Card: " + this.card.getID() + " entered column: " + this.toColumnID, eventList.get(1).getSourceName());
    }

    @Test
    public void the_card_cannot_be_moved_to_the_column_when_WIP_limit_is_reached() {
        SetColumnWIPUseCase setColumnWIPUseCase = new SetColumnWIPUseCase(this.eventBus, this.boardRepository);
        SetColumnWIPUseCaseInput setColumnWIPUseCaseInput = new SetColumnWIPUseCaseInput();
        SetColumnWIPUseCaseOutput setColumnWIPUseCaseOutput = new SetColumnWIPUseCaseOutput();

        setColumnWIPUseCaseInput.setBoardID(this.boardID.toString());
        setColumnWIPUseCaseInput.setColumnID(this.toColumnID);
        setColumnWIPUseCaseInput.setColumnWIP(1);

        setColumnWIPUseCase.execute(setColumnWIPUseCaseInput, setColumnWIPUseCaseOutput);

        MoveCardUseCase moveCardUseCase = new MoveCardUseCase(this.eventBus, this.boardRepository);
        MoveCardUseCaseInput moveCardUseCaseInput = new MoveCardUseCaseInput();
        MoveCardUseCaseOutput moveCardUseCaseOutput = new MoveCardUseCaseOutput();

        moveCardUseCaseInput.setBoardID(this.boardID);
        moveCardUseCaseInput.setCardID(this.card.getID().toString());
        moveCardUseCaseInput.setFromColumnID(this.fromColumnID);
        moveCardUseCaseInput.setToColumnID(this.toColumnID);

        moveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);


        try {
            moveCardUseCaseInput.setCardID(this.card2.getID().toString());
            moveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);
        } catch (IllegalStateException e) {
            assertEquals("The card cannot be moved to the column that has achieved its WIP limit.", e.getMessage());
            return;
        }
        fail("The card cannot be moved to the column that has achieved its WIP limit.");
    }

}