package phd.sa.csie.ntut.edu.tw.controller.repository.mysql;

import java.sql.*;
import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.controller.database.DB_connector;
import phd.sa.csie.ntut.edu.tw.usecase.DTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class MysqlCardRepository implements CardRepository {

  // TODO DI for the DB connector

  @Override
  public void add(DTO entity) {
    try {
      Connection connection = DB_connector.getConnection();
      PreparedStatement stmt = connection.prepareStatement("INSERT INTO Card VALUES(?, ?, ?)");
      stmt.setString(1, entity.getId().toString());
      stmt.setString(2, entity.getName());
      stmt.setString(3, null);

      stmt.executeUpdate();
      DB_connector.closeConnection(connection);
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public DTO findById(UUID uuid) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  @Override
  public void save(DTO entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Not implemented yet.");
  }

}