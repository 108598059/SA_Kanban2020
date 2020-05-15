package phd.sa.csie.ntut.edu.tw.adapter.repository.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import phd.sa.csie.ntut.edu.tw.adapter.database.DB_connector;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class MysqlCardRepository extends CardRepository {
  @Override
  public void save(CardDTO cardDTO) {
    try {
      Connection connection = DB_connector.getConnection();
      PreparedStatement stmt = connection.prepareStatement("INSERT INTO Card VALUES(?, ?, ?)");
      stmt.setString(1, cardDTO.getId());
      stmt.setString(2, cardDTO.getName());
      stmt.setString(3, cardDTO.getColumnId());

      stmt.executeUpdate();
      DB_connector.closeConnection(connection);
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public void update(CardDTO cardDTO) {
    try {
      Connection connection = DB_connector.getConnection();
      PreparedStatement stmt = connection.prepareStatement(
              "UPDATE `Card` " +
                  "SET `Name`=?,`ColumnID`=? " +
                  "WHERE `ID`=?");
      stmt.setString(1, cardDTO.getName());
      stmt.setString(2, cardDTO.getColumnId());
      stmt.setString(3, cardDTO.getId());

      stmt.executeUpdate();
      DB_connector.closeConnection(connection);
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage());
    }

  }

  @Override
  public CardDTO findById(String id) {
    try {
      Connection connection = DB_connector.getConnection();
      PreparedStatement stmt = connection.prepareStatement("SELECT * FROM `Card` WHERE ID=?");
      stmt.setString(1, id);

      ResultSet resultSet = stmt.executeQuery();

      CardDTO result = new CardDTO();
      while (resultSet.next()) {
        result.setId(resultSet.getString("ID"));
        result.setName(resultSet.getString("Name"));
        result.setColumnId(resultSet.getString("ColumnID"));
      }

      DB_connector.closeConnection(connection);
      return result;
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}