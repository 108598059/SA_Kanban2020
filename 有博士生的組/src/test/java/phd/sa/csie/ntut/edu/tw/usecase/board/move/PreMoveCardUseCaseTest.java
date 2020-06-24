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
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.column.setwip.SetColumnWIPUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.setwip.SetColumnWIPUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.column.setwip.SetColumnWIPUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.board.CardPreMovedEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.card.CardCreatedEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.domain.EventSourcingHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.domain.dto.DomainEventDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.card.CardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.event.EventLogRepository;

import static org.junit.Assert.*;

public class PreMoveCardUseCaseTest {

    private BoardRepository boardRepository;
    private Card card;
    private Card card2;
    private String fromColumnID;
    private String toColumnID;
    private String boardID;
    private DomainEventBus eventBus;

    @Before
    public void given_two_cards_and_a_board_and_two_columns() {
        this.eventBus = new DomainEventBus();
        CardRepository cardRepository = new MemoryCardRepository();
        this.boardRepository = new MemoryBoardRepository();
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(this.eventBus, cardRepository, this.boardRepository);

        Board board = new Board(UUID.randomUUID(), "Kanban");
        board.createColumn("Backlog", 0);
        board.createColumn("Archive", 1);

        this.fromColumnID = board.get(0).getID().toString();
        this.toColumnID = board.get(1).getID().toString();

        this.boardID = board.getID().toString();
        this.boardRepository.save(BoardDTOConverter.toDTO(board));
        this.eventBus.register(new CardCreatedEventHandler(this.eventBus, cardRepository, this.boardRepository));

        CardPreMovedEventHandler cardPreMovedEventHandler = new CardPreMovedEventHandler(this.eventBus, this.boardRepository);
        this.eventBus.register(cardPreMovedEventHandler);

        CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
        CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardPresenter();

        createCardUseCaseInput.setCardName("Implement a column");
        createCardUseCaseInput.setBoardID(this.boardID);
        createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);
        this.card = CardDTOConverter.toEntity(cardRepository.findByID(createCardUseCaseOutput.getCardID()));

        createCardUseCaseInput.setCardName("Implement a board");
        createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);
        this.card2 = CardDTOConverter.toEntity(cardRepository.findByID(createCardUseCaseOutput.getCardID()));
    }

    @Test
    public void the_to_column_should_contain_the_moved_card () {
        PreMoveCardUseCase preMoveCardUseCase = new PreMoveCardUseCase(this.eventBus, this.boardRepository);
        MoveCardUseCaseInput moveCardUseCaseInput = new MoveCardUseCaseInput();
        MoveCardUseCaseOutput moveCardUseCaseOutput = new MoveCardUseCaseOutput();

        moveCardUseCaseInput.setBoardID(this.boardID);
        moveCardUseCaseInput.setCardID(this.card.getID().toString());
        moveCardUseCaseInput.setFromColumnID(this.fromColumnID);
        moveCardUseCaseInput.setToColumnID(this.toColumnID);

        preMoveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);

        assertEquals(this.card.getID().toString(), moveCardUseCaseOutput.getCardID());
        assertEquals(this.fromColumnID, moveCardUseCaseOutput.getFromColumnID());
        assertEquals(this.toColumnID, moveCardUseCaseOutput.getToColumnID());

        Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(this.boardID));
        assertTrue(board.findColumnByID(UUID.fromString(this.toColumnID)).cardExist(this.card.getID()));
        assertFalse(board.findColumnByID(UUID.fromString(this.fromColumnID)).cardExist(this.card.getID()));
    }

    @Test
    public void pre_move_card_should_post_card_pre_moved_event() {
        DomainEventBus eventBusWithoutPreMovedHandler = new DomainEventBus();
        EventLogRepository eventLogRepository = new MemoryEventLogRepository();
        eventBusWithoutPreMovedHandler.register(new EventSourcingHandler(eventLogRepository));

        PreMoveCardUseCase preMoveCardUseCase = new PreMoveCardUseCase(eventBusWithoutPreMovedHandler, this.boardRepository);
        MoveCardUseCaseInput moveCardUseCaseInput = new MoveCardUseCaseInput();
        MoveCardUseCaseOutput moveCardUseCaseOutput = new MoveCardUseCaseOutput();

        moveCardUseCaseInput.setBoardID(this.boardID);
        moveCardUseCaseInput.setCardID(this.card.getID().toString());
        moveCardUseCaseInput.setFromColumnID(this.fromColumnID);
        moveCardUseCaseInput.setToColumnID(this.toColumnID);

        assertEquals(0, eventLogRepository.size());

        preMoveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);

        assertEquals(1, eventLogRepository.size());

        List<DomainEventDTO> eventList = eventLogRepository.getAll();

        assertEquals("[Card Pre-moved Event] Card: " + this.card.getID() + " pre-moved from the column: " +
                this.fromColumnID + " to the column: " + this.toColumnID, eventList.get(0).getSourceName());
    }

    @Test
    public void if_pre_move_to_a_column_reached_wip_limit_the_card_should_not_be_moved() {
        SetColumnWIPUseCase setColumnWIPUseCase = new SetColumnWIPUseCase(this.eventBus, this.boardRepository);
        SetColumnWIPUseCaseInput setColumnWIPUseCaseInput = new SetColumnWIPUseCaseInput();
        SetColumnWIPUseCaseOutput setColumnWIPUseCaseOutput = new SetColumnWIPUseCaseOutput();

        setColumnWIPUseCaseInput.setBoardID(this.boardID);
        setColumnWIPUseCaseInput.setColumnID(this.toColumnID);
        setColumnWIPUseCaseInput.setColumnWIP(1);

        setColumnWIPUseCase.execute(setColumnWIPUseCaseInput, setColumnWIPUseCaseOutput);

        PreMoveCardUseCase preMoveCardUseCase = new PreMoveCardUseCase(this.eventBus, this.boardRepository);
        MoveCardUseCaseInput moveCardUseCaseInput = new MoveCardUseCaseInput();
        MoveCardUseCaseOutput moveCardUseCaseOutput = new MoveCardUseCaseOutput();

        moveCardUseCaseInput.setBoardID(this.boardID);
        moveCardUseCaseInput.setCardID(this.card.getID().toString());
        moveCardUseCaseInput.setFromColumnID(this.fromColumnID);
        moveCardUseCaseInput.setToColumnID(this.toColumnID);

        preMoveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);

        moveCardUseCaseInput.setCardID(this.card2.getID().toString());
        Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(this.boardID));

        assertTrue(board.findColumnByID(UUID.fromString(this.fromColumnID)).cardExist(this.card2.getID()));

        preMoveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);

        board = BoardDTOConverter.toEntity(this.boardRepository.findByID(this.boardID));
        assertTrue(board.findColumnByID(UUID.fromString(this.toColumnID)).cardExist(this.card.getID()));
        assertFalse(board.findColumnByID(UUID.fromString(this.fromColumnID)).cardExist(this.card.getID()));
        assertFalse(board.findColumnByID(UUID.fromString(this.toColumnID)).cardExist(this.card2.getID()));
        assertTrue(board.findColumnByID(UUID.fromString(this.fromColumnID)).cardExist(this.card2.getID()));
    }
}