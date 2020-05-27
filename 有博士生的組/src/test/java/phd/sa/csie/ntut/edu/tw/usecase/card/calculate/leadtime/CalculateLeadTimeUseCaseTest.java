package phd.sa.csie.ntut.edu.tw.usecase.card.calculate.leadtime;

import org.junit.Before;
import org.junit.Test;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryEventLogRepository;
import phd.sa.csie.ntut.edu.tw.model.DomainEvent;
import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.board.commit.card.CommitCardUseCase;
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
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.card.CardEnteredColumnEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.EventSourcingHandler;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.EventLogRepository;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class CalculateLeadTimeUseCaseTest {
    private EventLogRepository eventLogRepository;
    private BoardRepository boardRepository;
    private CardRepository cardRepository;
    private DomainEventBus eventBus;
    private Board board;
    private Card card;
    private String fromColumnID;

    @Before
    public void create_a_card_and_add_to_a_board() {
        this.eventBus = new DomainEventBus();
        this.cardRepository = new MemoryCardRepository();
        this.boardRepository = new MemoryBoardRepository();
        this.eventLogRepository = new MemoryEventLogRepository();

        CreateCardUseCase createCardUseCase = new CreateCardUseCase(this.eventBus, this.cardRepository, this.boardRepository);
//        CreateColumnUseCase createColumnUseCase = new CreateColumnUseCase(this.eventBus, this.boardRepository);

        this.board = new Board(UUID.randomUUID(), "Kanban");
        this.boardRepository.save(BoardDTOConverter.toDTO(board));
        this.eventBus.register(new CommitCardUseCase(this.eventBus, this.cardRepository, this.boardRepository));

        CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
        CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();

        createCardUseCaseInput.setCardName("Calculate lead time");
        createCardUseCaseInput.setBoardID(this.board.getID().toString());
        createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);
        this.card = CardDTOConverter.toEntity(this.cardRepository.findByID(createCardUseCaseOutput.getCardID()));

        DomainEventHandler cardEnterColumnEventHandler = new CardEnteredColumnEventHandler(this.eventBus, this.cardRepository);
        this.eventBus.register(cardEnterColumnEventHandler);

        DomainEventHandler<DomainEvent> eventSourcingHandler = new EventSourcingHandler(this.eventLogRepository);
        this.eventBus.register(eventSourcingHandler);


//        CreateColumnUseCaseInput createColumnUseCaseInput = new CreateColumnUseCaseInput();
//        CreateColumnUseCaseOutput createColumnUseCaseOutput = new CreateColumnUseCaseOutput();
//
//        createColumnUseCaseInput.setBoardID(this.boardID.toString());
//        createColumnUseCaseInput.setTitle("develop");
//
//        createColumnUseCase.execute(createColumnUseCaseInput, createColumnUseCaseOutput);

//        String toColumnID = createColumnUseCaseOutput.getID();
    }

    @Test
    public void calculate_lead_time_for_a_card_from_backlog_column_to_archive_column() throws Exception {
        // Lead time greater than 5 seconds.
        Thread.sleep(5000);

        MoveCardUseCase moveCardUseCase = new MoveCardUseCase(this.eventBus, this.boardRepository);
        MoveCardUseCaseInput moveCardUseCaseInput = new MoveCardUseCaseInput();
        MoveCardUseCaseOutput moveCardUseCaseOutput = new MoveCardUseCaseOutput();

        moveCardUseCaseInput.setBoardID(this.board.getID().toString());
        moveCardUseCaseInput.setCardID(this.card.getID().toString());
        moveCardUseCaseInput.setFromColumnID(this.board.getBacklogColumn().getID().toString());
        moveCardUseCaseInput.setToColumnID(this.board.getArchiveColumn().getID().toString());

        moveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);

//        CalculateLeadTimeUseCase calculateLeadTimeUseCase = new CalculateLeadTimeUseCase(this.eventBus, this.eventLogRepository);
//        CalculateLeadTimeUseCaseInput calculateLeadTimeUseCaseInput = new CalculateLeadTimeUseCaseInput();
//        CalculateLeadTimeUseCaseOutput calculateLeadTimeUseCaseOutput = new CalculateLeadTimeUseCaseOutput();
//
//        calculateLeadTimeUseCaseInput.setCardID(this.card.getID().toString());
//        calculateLeadTimeUseCaseInput.setBoardID(this.board.getID().toString());
//
//        calculateLeadTimeUseCase.execute(calculateLeadTimeUseCaseInput, calculateLeadTimeUseCaseOutput);
//
//        assertTrue(calculateLeadTimeUseCaseOutput.getLeadTime() > 5000);
    }
}
