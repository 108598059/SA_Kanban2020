package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.adapter.database.DB_connector;
import phd.sa.csie.ntut.edu.tw.adapter.presenter.card.create.CreateCardPresenter;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.board.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.mysql.card.MysqlCardRepository;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.aggregate.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.card.CardCreatedEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.card.CardRepository;

import static org.junit.Assert.*;

public class MysqlCreateCardUseCaseTest {
    private CardRepository cardRepository;
    private BoardRepository boardRepository;
    private String cardID;
    private Board board;
    private DomainEventBus eventBus;

    @Before
    public void given_a_board() {
        this.cardRepository = new MysqlCardRepository();
        this.boardRepository = new MemoryBoardRepository();
        this.board = new Board(UUID.randomUUID(), "Kanban");
        this.boardRepository.save(BoardDTOConverter.toDTO(this.board));

        this.eventBus = new DomainEventBus();
        this.eventBus.register(new CardCreatedEventHandler(this.eventBus, this.cardRepository, this.boardRepository));
    }

    @After
    public void tearDown() throws SQLException {
        Connection conn = DB_connector.getConnection();
        PreparedStatement statement = conn.prepareStatement("DELETE FROM Card Where ID = ?");
        statement.setString(1, this.cardID);
        statement.executeUpdate();
        DB_connector.closeConnection(conn);
    }

    @Test
    public void create_card_should_save_card_to_database() {
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(this.eventBus, this.cardRepository);
        CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
        CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardPresenter();

        createCardUseCaseInput.setCardName("Create a card");
        createCardUseCaseInput.setBoardID(this.board.getID().toString());

        createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);

        assertEquals("Create a card", createCardUseCaseOutput.getCardName());
        assertNotEquals("", createCardUseCaseOutput.getCardID());
        assertNotNull(createCardUseCaseOutput.getCardID());
        this.cardID = createCardUseCaseOutput.getCardID();
        CardDTO cardDTO = this.cardRepository.findByID(this.cardID);
        assertEquals("Create a card", cardDTO.getName());
    }

}