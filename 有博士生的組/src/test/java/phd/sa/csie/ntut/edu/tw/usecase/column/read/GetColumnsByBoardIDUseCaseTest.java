package phd.sa.csie.ntut.edu.tw.usecase.column.read;

import org.junit.Before;
import org.junit.Test;
import phd.sa.csie.ntut.edu.tw.adapter.presenter.card.create.CreateCardPresenter;
import phd.sa.csie.ntut.edu.tw.adapter.presenter.column.read.GetColumnsByBoardIDPresenter;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.board.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.card.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.card.CardCreatedEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.DomainEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.card.CardRepository;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetColumnsByBoardIDUseCaseTest {
    private BoardRepository boardRepository;
    private CardRepository cardRepository;
    private UUID boardID;

    @Before
    public void create_two_cards_in_a_board() {
        this.boardRepository = new MemoryBoardRepository();
        this.cardRepository = new MemoryCardRepository();

        Board board = new Board(UUID.randomUUID(), "Kanban");
        this.boardID = board.getID();
        this.boardRepository.save(BoardDTOConverter.toDTO(board));

        DomainEventBus eventBus = new DomainEventBus();
        DomainEventHandler cardCreatedEventHandler = new CardCreatedEventHandler(eventBus, this.cardRepository, this.boardRepository);
        eventBus.register(cardCreatedEventHandler);

        CreateCardUseCase createCardUseCase = new CreateCardUseCase(eventBus, this.cardRepository, this.boardRepository);
        CreateCardUseCaseInput createCardInput = new CreateCardUseCaseInput();
        CreateCardUseCaseOutput createCardOutput = new CreateCardPresenter();

        createCardInput.setBoardID(board.getID().toString());
        createCardInput.setCardName("Implement a column");

        createCardUseCase.execute(createCardInput, createCardOutput);

        createCardInput.setCardName("Implement a card");

        createCardUseCase.execute(createCardInput, createCardOutput);
    }

    @Test
    public void test_get_columns_structure_by_board_id() {
        GetColumnsByBoardIDUseCase getColumnsByBoardIDUseCase = new GetColumnsByBoardIDUseCase(this.boardRepository, this.cardRepository);
        GetColumnsByBoardIDUseCaseInput getColumnsByBoardIDUsecaseInput = new GetColumnsByBoardIDUseCaseInput();
        GetColumnsByBoardIDUseCaseOutput getColumnsByBoardIDUsecaseOutput = new GetColumnsByBoardIDPresenter();

        getColumnsByBoardIDUsecaseInput.setBoardID(this.boardID.toString());

        getColumnsByBoardIDUseCase.execute(getColumnsByBoardIDUsecaseInput, getColumnsByBoardIDUsecaseOutput);

        assertEquals(2, getColumnsByBoardIDUsecaseOutput.getColumnList().size());
        GetColumnsByBoardIDUseCaseOutput.ColumnViewObject backlogColumn = getColumnsByBoardIDUsecaseOutput.getColumnList().get(0);
        assertEquals("Backlog", backlogColumn.getTitle());
        assertNotNull(backlogColumn.getID());
        assertEquals(0, backlogColumn.getWIP());
        assertEquals(2, backlogColumn.getCardList().size());
        assertEquals("Implement a column", backlogColumn.getCardList().get(0).getName());
        assertEquals("Implement a card", backlogColumn.getCardList().get(1).getName());
        assertNotNull(backlogColumn.getCardList().get(0).getID());
    }
}
