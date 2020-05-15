package phd.sa.csie.ntut.edu.tw.usecase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.controller.database.DB_connector;
import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.controller.repository.mysql.MysqlCardRepository;
import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
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
    cardRepository = new MysqlCardRepository();
    boardRepository = new MemoryBoardRepository();
    board = new Board("Kanban");
    boardRepository.save(BoardDTOConverter.toDTO(board));
    eventBus = new DomainEventBus();
  }

  @Test
  public void createCard() throws Exception {
    CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, eventBus);
    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();

    createCardUseCaseInput.setCardName("Card1");
    createCardUseCaseInput.setBoardId(board.getId());
    createCardUseCaseInput.setColumnId(board.get(0).getId());

    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);

    assertEquals("Card1", createCardUseCaseOutput.getCardName());
    assertNotEquals("", createCardUseCaseOutput.getCardId());
    assertNotNull(createCardUseCaseOutput.getCardId());
    cardID = createCardUseCaseOutput.getCardId();
    CardDTO cardDTO = cardRepository.findById(cardID);
    assertEquals("Card1", cardDTO.getName());
  }

  @After
  public void tearDown() throws SQLException {
    Connection conn = DB_connector.getConnection();
    PreparedStatement statement = conn.prepareStatement("DELETE FROM Card Where ID = ?");
    statement.setString(1, cardID);
    statement.executeUpdate();
    DB_connector.closeConnection(conn);
  }

}