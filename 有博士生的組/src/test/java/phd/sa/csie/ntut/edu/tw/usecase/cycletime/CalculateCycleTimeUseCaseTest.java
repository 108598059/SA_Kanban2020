package phd.sa.csie.ntut.edu.tw.usecase.cycletime;

import org.junit.Before;
import org.junit.Test;
import phd.sa.csie.ntut.edu.tw.adapter.presenter.card.create.CreateCardPresenter;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.board.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.event.MemoryCardEnteredColumnEventRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.event.MemoryCardLeftColumnEventRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.card.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.aggregate.board.Board;
import phd.sa.csie.ntut.edu.tw.model.aggregate.card.Card;
import phd.sa.csie.ntut.edu.tw.model.common.DateProvider;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.board.move.PreMoveCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.move.MoveCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.board.move.MoveCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.board.CardPreMovedEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.card.CardCreatedEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.MoveCardEventSourcingHandler;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.event.CardEnteredColumnEventRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.event.CardLeftColumnEventRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.card.CardRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CalculateCycleTimeUseCaseTest {
    private DomainEventBus eventBus;
    private CardRepository cardRepository;
    private BoardRepository boardRepository;
    private CardEnteredColumnEventRepository cardEnteredColumnEventRepository;
    private CardLeftColumnEventRepository cardLeftColumnEventRepository;

    private Board board;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Before
    public void setup() {
        this.eventBus = new DomainEventBus();
        this.cardRepository = new MemoryCardRepository();
        this.boardRepository = new MemoryBoardRepository();
        this.cardEnteredColumnEventRepository = new MemoryCardEnteredColumnEventRepository();
        this.cardLeftColumnEventRepository = new MemoryCardLeftColumnEventRepository();
        MoveCardEventSourcingHandler moveCardEventSourcingHandler = new MoveCardEventSourcingHandler(this.cardEnteredColumnEventRepository,
                this.cardLeftColumnEventRepository);
        this.eventBus.register(moveCardEventSourcingHandler);

        this.board = new Board(UUID.randomUUID(), "Kanban");
        this.board.createColumn("Backlog", 0);
        this.board.createColumn("Develop", 1);
        this.board.createColumn("Archive", 2);

        this.boardRepository.save(BoardDTOConverter.toDTO(board));

        CardCreatedEventHandler cardCreatedEventHandler = new CardCreatedEventHandler(this.eventBus, this.cardRepository, this.boardRepository);
        this.eventBus.register(cardCreatedEventHandler);
        this.eventBus.register(new CardPreMovedEventHandler(this.eventBus, this.boardRepository));

        this.board = BoardDTOConverter.toEntity(this.boardRepository.findByID(this.board.getID().toString()));
    }

    @Test
    public void calculate_cycle_time() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2020-05-21 00:00:00"));
        Card card = this.create_card("Calculate Cycle Time");

        DateProvider.setDate(dateFormat.parse("2020-05-22 00:00:00"));
        this.move_card(card, this.board.get(0).getID().toString(), this.board.get(1).getID().toString());

        DateProvider.setDate(dateFormat.parse("2020-05-27 00:00:00"));
        this.move_card(card, this.board.get(1).getID().toString(), this.board.get(2).getID().toString());

        CalculateCycleTimeUseCase calculateCycleTimeUseCase = new CalculateCycleTimeUseCase(this.eventBus, this.cardEnteredColumnEventRepository, this.cardLeftColumnEventRepository);
        CalculateCycleTimeUseCaseInput calculateCycleTimeUseCaseInput = new CalculateCycleTimeUseCaseInput();
        CalculateCycleTimeUseCaseOutput calculateCycleTimeUseCaseOutput = new CalculateCycleTimeUseCaseOutput();

        calculateCycleTimeUseCaseInput.setCardID(card.getID().toString());
        calculateCycleTimeUseCaseInput.setStartColumnID(this.board.get(0).getID().toString());
        calculateCycleTimeUseCaseInput.setEndColumnID(this.board.get(0).getID().toString());
        calculateCycleTimeUseCaseInput.setStartTime(dateFormat.parse("2020-05-21 00:00:00"));
        calculateCycleTimeUseCaseInput.setEndTime(dateFormat.parse("2020-05-27 00:00:00"));
        calculateCycleTimeUseCase.execute(calculateCycleTimeUseCaseInput, calculateCycleTimeUseCaseOutput);

        assertEquals(card.getID().toString(), calculateCycleTimeUseCaseOutput.getCardID());
        assertEquals(this.board.get(0).getID().toString(), calculateCycleTimeUseCaseOutput.getStartColumnID());
        assertEquals(this.board.get(0).getID().toString(), calculateCycleTimeUseCaseOutput.getEndColumnID());
        assertEquals(24 * 60 * 60 * 1000, calculateCycleTimeUseCaseOutput.getCycleTime());
    }

    private Card create_card(String cardName) {
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(this.eventBus, this.cardRepository);
        CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
        CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardPresenter();

        createCardUseCaseInput.setCardName(cardName);
        createCardUseCaseInput.setBoardID(this.board.getID().toString());
        createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);
        return CardDTOConverter.toEntity(this.cardRepository.findByID(createCardUseCaseOutput.getCardID()));
    }

    private void move_card(Card card, String fromColumnID, String toColumnID) {
        PreMoveCardUseCase preMoveCardUseCase = new PreMoveCardUseCase(this.eventBus, this.boardRepository);
        MoveCardUseCaseInput moveCardUseCaseInput = new MoveCardUseCaseInput();
        MoveCardUseCaseOutput moveCardUseCaseOutput = new MoveCardUseCaseOutput();

        moveCardUseCaseInput.setBoardID(this.board.getID().toString());
        moveCardUseCaseInput.setCardID(card.getID().toString());
        moveCardUseCaseInput.setFromColumnID(fromColumnID);
        moveCardUseCaseInput.setToColumnID(toColumnID);

        preMoveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);
    }
}
