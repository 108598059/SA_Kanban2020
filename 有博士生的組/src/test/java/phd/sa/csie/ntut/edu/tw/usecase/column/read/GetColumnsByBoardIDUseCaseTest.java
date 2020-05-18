package phd.sa.csie.ntut.edu.tw.usecase.column.read;

import org.junit.Before;
import org.junit.Test;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.CardCreatedEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.DomainEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetColumnsByBoardIDUseCaseTest {
    private BoardRepository boardRepository;
    private CardRepository cardRepository;
    private UUID boardID;

    @Before
    public void add_two_cards_to_a_board() {
        this.boardRepository = new MemoryBoardRepository();
        this.cardRepository = new MemoryCardRepository();

        Board board = new Board("Kanban");
        this.boardID = board.getId();
        this.boardRepository.save(BoardDTOConverter.toDTO(board));

        DomainEventHandler cardCreatedEventHandler = new CardCreatedEventHandler(this.cardRepository, this.boardRepository);
        DomainEventBus eventBus = new DomainEventBus();
        eventBus.register(cardCreatedEventHandler);

        CreateCardUseCase createCardUseCase = new CreateCardUseCase(eventBus, this.cardRepository, this.boardRepository);
        CreateCardUseCaseInput createCardInput = new CreateCardUseCaseInput();
        CreateCardUseCaseOutput createCardOutput = new CreateCardUseCaseOutput();
        createCardInput.setBoardID(board.getId().toString());

        createCardInput.setCardName("Card1");
        createCardUseCase.execute(createCardInput, createCardOutput);

        createCardInput.setCardName("Card2");
        createCardUseCase.execute(createCardInput, createCardOutput);
    }

    @Test
    public void test_get_columns_structure_by_board_id() {
        GetColumnsByBoardIDUseCase usecase = new GetColumnsByBoardIDUseCase(this.boardRepository, this.cardRepository);
        GetColumnsByBoardIDUsecaseInput input = new GetColumnsByBoardIDUsecaseInput();
        GetColumnsByBoardIDUsecaseOutput output = new GetColumnsByBoardIDUsecaseOutput();

        input.setBoardID(this.boardID.toString());

        usecase.execute(input, output);

        assertEquals(2, output.getColumnList().size());
        GetColumnsByBoardIDUsecaseOutput.ColumnViewObject column1 = output.getColumnList().get(0);
        assertEquals("Backlog", column1.getTitle());
        assertNotNull(column1.getId());
        assertEquals(0, column1.getWip());
        assertEquals(2, column1.getCardList().size());
        assertEquals("Card1", column1.getCardList().get(0).getName());
        assertNotNull(column1.getCardList().get(0).getId());

    }
}
