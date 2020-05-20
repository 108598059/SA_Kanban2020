package kanban.domain.adapter.repository.card;

import kanban.domain.adapter.database.table.CardTable;
import kanban.domain.adapter.database.MySqlDatabaseHelper;
import kanban.domain.model.aggregate.card.Card;
import kanban.domain.usecase.card.CardEntity;
import kanban.domain.usecase.card.repository.ICardRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlCardRepository implements ICardRepository {

    private MySqlDatabaseHelper sqlDatabaseHelper;

    public MySqlCardRepository() {
        sqlDatabaseHelper = new MySqlDatabaseHelper();
    }

    @Override
    public void add(CardEntity cardEntity) {
        sqlDatabaseHelper.connectToDatabase();
        PreparedStatement preparedStatement = null;
        try {
            sqlDatabaseHelper.transactionStart();

            String sql = String.format("Insert Into %s Values (?, ?, ?, ?, ?)",
                    CardTable.tableName);
            preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
            preparedStatement.setString(1, cardEntity.getCardId());
            preparedStatement.setString(2, cardEntity.getName());
            preparedStatement.setString(3, cardEntity.getDescription());
            preparedStatement.setString(4, cardEntity.getType());
            preparedStatement.setString(5, cardEntity.getSize());
            preparedStatement.executeUpdate();
            sqlDatabaseHelper.transactionEnd();
        } catch (SQLException e) {
            sqlDatabaseHelper.transactionError();
            e.printStackTrace();
        } finally {
            sqlDatabaseHelper.closePreparedStatement(preparedStatement);
            sqlDatabaseHelper.closeConnection();
        }
    }

    @Override
    public CardEntity getCardById(String cardId) {
        if(!sqlDatabaseHelper.isTransacting()) {
            sqlDatabaseHelper.connectToDatabase();
        }
        ResultSet resultSet = null;
        CardEntity cardEntity = null;
        try {
            String query = String.format("Select * From %s Where %s = '%s'",
                    CardTable.tableName,
                    CardTable.cardId,
                    cardId);
            resultSet = sqlDatabaseHelper.getResultSet(query);
            if (resultSet.first()) {
                String name = resultSet.getString(CardTable.name);
                String description = resultSet.getString(CardTable.description);
                String type = resultSet.getString(CardTable.type);
                String size = resultSet.getString(CardTable.size);

                cardEntity = new CardEntity();
                cardEntity.setCardId(cardId);
                cardEntity.setName(name);
                cardEntity.setDescription(description);
                cardEntity.setType(type);
                cardEntity.setSize(size);

            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sqlDatabaseHelper.closeResultSet(resultSet);
            if(!sqlDatabaseHelper.isTransacting()) {
                sqlDatabaseHelper.closeConnection();
            }
        }

        if(cardEntity == null) {
            throw new RuntimeException("Card is not found,id=" + cardId);
        }

        return cardEntity;
    }

    @Override
    public void save(CardEntity cardEntity) {
        sqlDatabaseHelper.connectToDatabase();
        PreparedStatement preparedStatement = null;
        try {
            sqlDatabaseHelper.transactionStart();

            String sql = String.format("Insert Into %s Values (? , ?, ?, ?, ?) On Duplicate Key Update %s=? %s=? %s=? %s=?",
                    CardTable.tableName, CardTable.name, CardTable.description, CardTable.type, CardTable.size);
            preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
            preparedStatement.setString(1, cardEntity.getCardId());
            preparedStatement.setString(2, cardEntity.getName());
            preparedStatement.setString(3, cardEntity.getDescription());
            preparedStatement.setString(4, cardEntity.getType());
            preparedStatement.setString(5, cardEntity.getSize());
            preparedStatement.setString(6, cardEntity.getName());
            preparedStatement.setString(7, cardEntity.getDescription());
            preparedStatement.setString(8, cardEntity.getType());
            preparedStatement.setString(9, cardEntity.getSize());
            preparedStatement.executeUpdate();
            sqlDatabaseHelper.transactionEnd();
        } catch (SQLException e) {
            sqlDatabaseHelper.transactionError();
            e.printStackTrace();
        } finally {
            sqlDatabaseHelper.closePreparedStatement(preparedStatement);
            sqlDatabaseHelper.closeConnection();
        }
    }
}
