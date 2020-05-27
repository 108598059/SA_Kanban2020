package phd.sa.csie.ntut.edu.tw.usecase.card.calculate.leadtime;

import org.junit.Before;
import org.junit.Test;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.board.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.card.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.event.MemoryCardEnteredColumnEventRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.event.MemoryCardLeftColumnEventRepository;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.model.common.DateProvider;
import phd.sa.csie.ntut.edu.tw.usecase.board.commit.card.CommitCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.board.move.MoveCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.move.MoveCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.board.move.MoveCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.DomainEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.card.CardCreatedEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.card.CardEnteredColumnEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.MoveCardEventSourcingHandler;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.card.CardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.event.CardEnteredColumnEventRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.event.CardLeftColumnEventRepository;

import java.text.SimpleDateFormat;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CalculateLeadTimeUseCaseTest {
    private BoardRepository boardRepository;
    private CardRepository cardRepository;
    private DomainEventBus eventBus;
    private Board board;
    private CardEnteredColumnEventRepository cardEnteredColumnEventRepository;
    private CardLeftColumnEventRepository cardLeftColumnEventRepository;
    private MoveCardEventSourcingHandler moveCardEventSourcingHandler;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Before
    public void create_a_card_and_add_to_a_board() {
        this.eventBus = new DomainEventBus();
        this.cardRepository = new MemoryCardRepository();
        this.boardRepository = new MemoryBoardRepository();
        this.cardEnteredColumnEventRepository = new MemoryCardEnteredColumnEventRepository();
        this.cardLeftColumnEventRepository = new MemoryCardLeftColumnEventRepository();

        CreateCardUseCase createCardUseCase = new CreateCardUseCase(this.eventBus, this.cardRepository, this.boardRepository);
        CreateColumnUseCase createColumnUseCase = new CreateColumnUseCase(this.eventBus, this.boardRepository);

        this.board = new Board(UUID.randomUUID(), "Kanban");
        this.boardRepository.save(BoardDTOConverter.toDTO(board));

        CardCreatedEventHandler cardCreatedEventHandler = new CardCreatedEventHandler(this.eventBus, this.cardRepository, this.boardRepository);
        this.eventBus.register(cardCreatedEventHandler);
        this.eventBus.register(new CommitCardUseCase(this.eventBus, this.cardRepository, this.boardRepository));

        DomainEventHandler cardEnterColumnEventHandler = new CardEnteredColumnEventHandler(this.eventBus, this.cardRepository);
        this.eventBus.register(cardEnterColumnEventHandler);

        MoveCardEventSourcingHandler moveCardEventSourcingHandler = new MoveCardEventSourcingHandler(this.cardEnteredColumnEventRepository,
                                                                                                     this.cardLeftColumnEventRepository);
        this.eventBus.register(moveCardEventSourcingHandler);

        CreateColumnUseCaseInput createColumnUseCaseInput = new CreateColumnUseCaseInput();
        CreateColumnUseCaseOutput createColumnUseCaseOutput = new CreateColumnUseCaseOutput();

        createColumnUseCaseInput.setBoardID(this.board.getID().toString());
        createColumnUseCaseInput.setTitle("develop");

        createColumnUseCase.execute(createColumnUseCaseInput, createColumnUseCaseOutput);
        this.board = BoardDTOConverter.toEntity(this.boardRepository.findByID(this.board.getID().toString()));
    }

    @Test
    public void calculate_lead_time_for_a_card_from_backlog_column_to_archive_column() throws Exception {
        DateProvider.setDate(dateFormat.parse("2020-05-21 00:00:00"));
        Card card = this.create_card("Calculate Cycle Time");

        DateProvider.setDate(dateFormat.parse("2020-05-22 00:00:00"));
        this.move_card(card, this.board.getBacklogColumn().getID().toString(), this.board.get(1).getID().toString());

        DateProvider.setDate(dateFormat.parse("2020-05-27 00:00:00"));
        this.move_card(card, this.board.get(1).getID().toString(), this.board.getArchiveColumn().getID().toString());

        CalculateLeadTimeUseCase calculateLeadTimeUseCase = new CalculateLeadTimeUseCase(this.eventBus,
                this.cardEnteredColumnEventRepository, this.cardLeftColumnEventRepository, this.cardRepository);
        CalculateLeadTimeUseCaseInput calculateLeadTimeUseCaseInput = new CalculateLeadTimeUseCaseInput();
        CalculateLeadTimeUseCaseOutput calculateLeadTimeUseCaseOutput = new CalculateLeadTimeUseCaseOutput();

        calculateLeadTimeUseCaseInput.setCardID(card.getID().toString());

        calculateLeadTimeUseCase.execute(calculateLeadTimeUseCaseInput, calculateLeadTimeUseCaseOutput);

        assertEquals(6 * 24 * 60 * 60 * 1000,
                     calculateLeadTimeUseCaseOutput.getLeadTime());

        CardDTO resultCard = this.cardRepository.findByID(card.getID().toString());
        assertEquals(calculateLeadTimeUseCaseOutput.getLeadTime(), resultCard.getLeadTime());
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
