package phd.sa.csie.ntut.edu.tw.usecase.card.calculate.cycletime;

import org.junit.Before;
import org.junit.Test;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.board.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.event.MemoryCardEnteredColumnEventRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.event.MemoryCardLeftColumnEventRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.card.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.model.common.DateProvider;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.board.move.MoveCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.move.MoveCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.board.move.MoveCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.DomainEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.card.CardCreatedEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.card.CardEnteredColumnEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.MoveCardEventSourcingHandler;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.event.CardEnteredColumnEventRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.event.CardLeftColumnEventRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.card.CardRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CalculateCycleTimeUseCaseTest {
    private DomainEventBus eventBus;
    private CardRepository cardRepository;
    private BoardRepository boardRepository;
    private CardEnteredColumnEventRepository cardEnteredColumnEventRepository;
    private CardLeftColumnEventRepository cardLeftColumnEventRepository;
    private MoveCardEventSourcingHandler moveCardEventSourcingHandler;

    private Board board;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Before
    public void setup() {
        this.eventBus = new DomainEventBus();
        this.cardRepository = new MemoryCardRepository();
        this.boardRepository = new MemoryBoardRepository();
        this.cardEnteredColumnEventRepository = new MemoryCardEnteredColumnEventRepository();
        this.cardLeftColumnEventRepository = new MemoryCardLeftColumnEventRepository();
        this.moveCardEventSourcingHandler = new MoveCardEventSourcingHandler(this.cardEnteredColumnEventRepository,
                                                                             this.cardLeftColumnEventRepository);
        CreateColumnUseCase createColumnUseCase = new CreateColumnUseCase(this.eventBus, this.boardRepository);
        this.eventBus.register(this.moveCardEventSourcingHandler);

        this.board = new Board(UUID.randomUUID(), "Kanban");
        this.boardRepository.save(BoardDTOConverter.toDTO(board));

        CardCreatedEventHandler cardCreatedEventHandler = new CardCreatedEventHandler(this.eventBus, this.cardRepository, this.boardRepository);
        this.eventBus.register(cardCreatedEventHandler);

        DomainEventHandler cardEnterColumnEventHandler = new CardEnteredColumnEventHandler(this.eventBus, this.cardRepository);
        this.eventBus.register(cardEnterColumnEventHandler);

        CreateColumnUseCaseInput createColumnUseCaseInput = new CreateColumnUseCaseInput();
        CreateColumnUseCaseOutput createColumnUseCaseOutput = new CreateColumnUseCaseOutput();

        createColumnUseCaseInput.setBoardID(this.board.getID().toString());
        createColumnUseCaseInput.setTitle("develop");

        createColumnUseCase.execute(createColumnUseCaseInput, createColumnUseCaseOutput);
        this.board = BoardDTOConverter.toEntity(this.boardRepository.findByID(this.board.getID().toString()));
    }

    @Test
    public void calculate_cycle_time() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2020-05-21 00:00:00"));
        Card card = this.create_card("Calculate Cycle Time");

        DateProvider.setDate(dateFormat.parse("2020-05-22 00:00:00"));
        this.move_card(card, this.board.getBacklogColumn().getID().toString(), this.board.get(1).getID().toString());

        DateProvider.setDate(dateFormat.parse("2020-05-27 00:00:00"));
        this.move_card(card, this.board.get(1).getID().toString(), this.board.getArchiveColumn().getID().toString());

        CalculateCycleTimeUseCase calculateCycleTimeUseCase = new CalculateCycleTimeUseCase(this.eventBus, this.cardEnteredColumnEventRepository, this.cardLeftColumnEventRepository);
        CalculateCycleTimeUseCaseInput calculateCycleTimeUseCaseInput = new CalculateCycleTimeUseCaseInput();
        CalculateCycleTimeUseCaseOutput calculateCycleTimeUseCaseOutput = new CalculateCycleTimeUseCaseOutput();

        calculateCycleTimeUseCaseInput.setCardID(card.getID().toString());
        calculateCycleTimeUseCase.execute(calculateCycleTimeUseCaseInput, calculateCycleTimeUseCaseOutput);

        List<CycleTime> cycleTimeList = calculateCycleTimeUseCaseOutput.getCycleTimeList();
        assertEquals(2, cycleTimeList.size());
        assertEquals(24 * 60 * 60 * 1000, cycleTimeList.get(0).getTime());
        assertEquals(5 * 24 * 60 * 60 * 1000, cycleTimeList.get(1).getTime());
    }

    private Card create_card(String cardName) {
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(this.eventBus, this.cardRepository, this.boardRepository);
        CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
        CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();

        createCardUseCaseInput.setCardName(cardName);
        createCardUseCaseInput.setBoardID(this.board.getID().toString());
        createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);
        return CardDTOConverter.toEntity(this.cardRepository.findByID(createCardUseCaseOutput.getCardID()));
    }

    private void move_card(Card card, String fromColumnID, String toColumnID) {
        MoveCardUseCase moveCardUseCase = new MoveCardUseCase(this.eventBus, this.boardRepository);
        MoveCardUseCaseInput moveCardUseCaseInput = new MoveCardUseCaseInput();
        MoveCardUseCaseOutput moveCardUseCaseOutput = new MoveCardUseCaseOutput();

        moveCardUseCaseInput.setBoardID(this.board.getID().toString());
        moveCardUseCaseInput.setCardID(card.getID().toString());
        moveCardUseCaseInput.setFromColumnID(fromColumnID);
        moveCardUseCaseInput.setToColumnID(toColumnID);

        moveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);
    }
}
