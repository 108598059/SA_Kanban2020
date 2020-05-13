package phd.sa.csie.ntut.edu.tw.usecase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.controller.database.DB_connector;
import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.controller.repository.mysql.MysqlCardRepository;
import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CommitCardUsecase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class MysqlCreateCardUseCaseTest {
  private CardRepository cardRepository;
  private BoardRepository boardRepository;
  private String cardID;
  private Board board;
  private DomainEventBus eventBus;
  private CommitCardUsecase commitCardUsecase;

  @Before
  public void setUp() {
    this.cardRepository = new MysqlCardRepository();
    this.boardRepository = new MemoryBoardRepository();
    this.board = new Board("Kanban");
    BoardDTOConverter boardDTOConverter = new BoardDTOConverter();
    this.boardRepository.save(boardDTOConverter.toDTO(this.board));
    this.eventBus = new DomainEventBus();
    this.commitCardUsecase = new CommitCardUsecase(this.cardRepository, this.boardRepository);
    this.eventBus.register(this.commitCardUsecase);

  }

  @Test
  public void createCard() throws Exception {
    CreateCardUseCase createCardUseCase = new CreateCardUseCase(this.eventBus);
    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();

    createCardUseCaseInput.setCardName("Card1");
    createCardUseCaseInput.setBoardID(this.board.getId().toString());

    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);

    assertEquals("Card1", createCardUseCaseOutput.getCardName());
    assertNotEquals("", createCardUseCaseOutput.getCardId());
    assertNotNull(createCardUseCaseOutput.getCardId());
    this.cardID = createCardUseCaseOutput.getCardId();
    CardDTO cardDTO = this.cardRepository.findById(this.cardID);
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