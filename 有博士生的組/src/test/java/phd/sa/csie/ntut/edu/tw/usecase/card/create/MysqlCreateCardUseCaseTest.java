package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.adapter.database.DB_connector;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.mysql.MysqlCardRepository;
import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.CardCreatedEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.DomainEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

import static org.junit.Assert.*;

public class MysqlCreateCardUseCaseTest {
  private CardRepository cardRepository;
  private BoardRepository boardRepository;
  private String cardID;
  private Board board;
  private DomainEventBus eventBus;

  @Before
  public void setUp() {
    this.cardRepository = new MysqlCardRepository();
    this.boardRepository = new MemoryBoardRepository();
    this.board = new Board(UUID.randomUUID(), "Kanban");
    this.boardRepository.save(BoardDTOConverter.toDTO(this.board));
    this.eventBus = new DomainEventBus();
    DomainEventHandler cardCreatedEventHandler = new CardCreatedEventHandler(this.cardRepository, this.boardRepository);
    this.eventBus.register(cardCreatedEventHandler);
  }

  @Test
  public void createCard() throws Exception {
    CreateCardUseCase createCardUseCase = new CreateCardUseCase(this.eventBus, this.cardRepository, this.boardRepository);
    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();

    createCardUseCaseInput.setCardName("Card1");
    createCardUseCaseInput.setBoardID(this.board.getID().toString());

    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);

    assertEquals("Card1", createCardUseCaseOutput.getCardName());
    assertNotEquals("", createCardUseCaseOutput.getCardID());
    assertNotNull(createCardUseCaseOutput.getCardID());
    this.cardID = createCardUseCaseOutput.getCardID();
    CardDTO cardDTO = this.cardRepository.findByID(this.cardID);
    assertEquals("Card1", cardDTO.getName());
  }

  @After
  public void tearDown() throws SQLException {
    Connection conn = DB_connector.getConnection();
    PreparedStatement statement = conn.prepareStatement("DELETE FROM Card Where ID = ?");
    statement.setString(1, this.cardID);
    statement.executeUpdate();
    DB_connector.closeConnection(conn);
  }

}