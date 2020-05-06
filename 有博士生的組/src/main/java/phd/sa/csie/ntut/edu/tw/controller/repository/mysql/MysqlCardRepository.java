package phd.sa.csie.ntut.edu.tw.controller.repository.mysql;

import java.sql.*;
import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.controller.database.DB_connector;
import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class MysqlCardRepository implements CardRepository {

  @Override
  public void add(Card card) {
    try {
      Connection connection = DB_connector.getConnection();
      PreparedStatement stmt = connection.prepareStatement("INSERT INTO Card VALUES(?, ?, ?)");
      stmt.setString(1, card.getId().toString());
      stmt.setString(2, card.getName());
      stmt.setString(3, null);

      stmt.executeUpdate();
      DB_connector.closeConnection(connection);
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public Card findCardByUUID(UUID uuid) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  @Override
  public void save(Card card) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Not implemented yet.");
  }

}