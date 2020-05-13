package phd.sa.csie.ntut.edu.tw.controller.repository.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.controller.database.DB_connector;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class MysqlCardRepository extends CardRepository {

  // TODO DI for the DB connector

  @Override
  public void save(CardDTO cardDTO) {
    try {
      Connection connection = DB_connector.getConnection();
      PreparedStatement stmt = connection.prepareStatement("INSERT INTO Card VALUES(?, ?, ?)");
      stmt.setString(1, cardDTO.getId().toString());
      stmt.setString(2, cardDTO.getName());
      stmt.setString(3, null);

      stmt.executeUpdate();
      DB_connector.closeConnection(connection);
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public CardDTO findById(UUID id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  @Override
  public void update(CardDTO cardDTO) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Not implemented yet.");
  }

}