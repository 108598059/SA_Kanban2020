package phd.sa.csie.ntut.edu.tw.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;
import phd.sa.csie.ntut.edu.tw.controller.database.DB_connector;
import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.controller.repository.mysql.MysqlCardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.*;

public class MysqlCreateCardUseCaseTest {

  private CardRepository cardRepository;
  private String cardID;

  @Before
  public void initialize() {
    cardRepository = new MysqlCardRepository();
  }

  @Test
  public void createCard() {
    CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository);
    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();
    createCardUseCaseInput.setCardName("Create Card");
    try {
      createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);
    } catch(Exception e) {
      assertEquals("", e.toString());
    }
    assertEquals("Create Card", createCardUseCaseOutput.getCardName());
    assertNotEquals("", createCardUseCaseOutput.getCardId());
    assertNotNull(createCardUseCaseOutput.getCardId());
    this.cardID = createCardUseCaseOutput.getCardId();
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