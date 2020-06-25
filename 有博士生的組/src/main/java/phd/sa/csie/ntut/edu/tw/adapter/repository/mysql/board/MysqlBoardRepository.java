package phd.sa.csie.ntut.edu.tw.adapter.repository.mysql.board;

import phd.sa.csie.ntut.edu.tw.adapter.database.DB_connector;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.column.dto.ColumnDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;

import javax.security.auth.login.Configuration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlBoardRepository extends BoardRepository {
    @Override
    public void save(BoardDTO boardDTO) {
        try {
            Connection connection = DB_connector.getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Board VALUES(?, ?, ?)");
            stmt.setString(1, boardDTO.getID());
            stmt.setString(2, boardDTO.getWorkspaceID());
            stmt.setString(3, boardDTO.getName());

            stmt.executeUpdate();

            List<ColumnDTO> columnDTOList = boardDTO.getColumnDTOs();
            for (ColumnDTO columnDTO: columnDTOList) {
                this.insertColumn(connection, columnDTO, boardDTO.getID(), columnDTOList.indexOf(columnDTO));
            }
            DB_connector.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(BoardDTO boardDTO) {
        try {
            Connection connection = DB_connector.getConnection();
            PreparedStatement stmt = connection.prepareStatement("UPDATE `Board` SET `WorkspaceID`=?, `Name`=? WHERE `ID`=?");
            stmt.setString(1, boardDTO.getWorkspaceID());
            stmt.setString(2, boardDTO.getName());
            stmt.setString(3, boardDTO.getID());

            stmt.executeUpdate();

            List<ColumnDTO> columnDTOList = boardDTO.getColumnDTOs();
            for (ColumnDTO columnDTO: columnDTOList) {

                PreparedStatement checkColumnStmt = connection.prepareStatement("SELECT * FROM `Column` " +
                        "WHERE `ID`=?");
                checkColumnStmt.setString(1, columnDTO.getID());
                ResultSet checkColumnResult = checkColumnStmt.executeQuery();
                if (checkColumnResult.next()) {
                    PreparedStatement columnStmt = connection.prepareStatement(
                            "UPDATE `Column` " +
                                "SET `Title`=?,`WIP`=?,`BoardID`=?,`Position`=? " +
                                "WHERE `ID`=?");
                    columnStmt.setString(1, columnDTO.getTitle());
                    columnStmt.setInt(2, columnDTO.getWIP());
                    columnStmt.setString(3, boardDTO.getID());
                    columnStmt.setInt(4, columnDTOList.indexOf(columnDTO));
                    columnStmt.setString(5, columnDTO.getID());;
                    columnStmt.executeUpdate();

                    for (String cardID: columnDTO.getCardIDs()) {
                        PreparedStatement checkCardColumnMapperStmt = connection.prepareStatement(
                                "SELECT * FROM `CardColumnMapper` WHERE `CardID`=?");
                        checkCardColumnMapperStmt.setString(1, cardID);
                        ResultSet checkCardColumnMapperResult = checkCardColumnMapperStmt.executeQuery();

                        if (checkCardColumnMapperResult.next()) {
                            PreparedStatement cardColumnMapperStmt = connection.prepareStatement(
                                    "UPDATE `CardColumnMapper` SET `ColumnID`=? WHERE `CardID`=?");
                            cardColumnMapperStmt.setString(1, columnDTO.getID());
                            cardColumnMapperStmt.setString(2, cardID);
                            cardColumnMapperStmt.executeUpdate();
                        } else {
                            PreparedStatement cardColumnMapperStmt = connection.prepareStatement(
                                    "INSERT INTO `CardColumnMapper`(`CardID`, `ColumnID`) " +
                                            "VALUES (?, ?)");
                            cardColumnMapperStmt.setString(1, cardID);
                            cardColumnMapperStmt.setString(2, columnDTO.getID());
                            cardColumnMapperStmt.executeUpdate();
                        }
                    }

                    PreparedStatement getPreservedPositionStmt = connection.prepareStatement(
                            "DELETE FROM `PreservedPositionMapper` WHERE `ColumnID`=?");
                    getPreservedPositionStmt.setString(1, columnDTO.getID());
                    getPreservedPositionStmt.executeUpdate();

                    for (String cardID: columnDTO.getPreservedPosition()) {
                        PreparedStatement preservedPositionMapperstmt = connection.prepareStatement(
                                "INSERT INTO `PreservedPositionMapper`(`CardID`, `ColumnID`) " +
                                        "VALUES (?, ?)");
                        preservedPositionMapperstmt.setString(1, cardID);
                        preservedPositionMapperstmt.setString(2, columnDTO.getID());
                        preservedPositionMapperstmt.executeUpdate();
                    }
                } else {
                    this.insertColumn(connection, columnDTO, boardDTO.getID(), columnDTOList.indexOf(columnDTO));
                }
            }

            DB_connector.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public BoardDTO findByID(String id) {
        try {
            Connection connection = DB_connector.getConnection();

            PreparedStatement findBoardStmt = connection.prepareStatement(
                    "SELECT * FROM `Board` WHERE `ID`=?"
            );

            findBoardStmt.setString(1, id);

            ResultSet findBoardResultSet = findBoardStmt.executeQuery();
            if (!findBoardResultSet.next()) {
                throw new RuntimeException("Board not found");
            }

            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setID(findBoardResultSet.getString("Board.ID"));
            boardDTO.setName(findBoardResultSet.getString("Board.Name"));
            boardDTO.setWorkspaceID(findBoardResultSet.getString("Board.WorkspaceID"));

            PreparedStatement findColumnsStmt = connection.prepareStatement(
                    "SELECT * FROM `Board`, `Column` " +
                        "WHERE `Board`.`ID`=? AND`Column`.`BoardID`=`Board`.`ID` " +
                        "ORDER BY `Column`.`Position` ASC");
            findColumnsStmt.setString(1, id);

            ResultSet resultSet = findColumnsStmt.executeQuery();

            List<ColumnDTO> columnDTOList = new ArrayList<>();
            while (resultSet.next()) {
                ColumnDTO columnDTO = new ColumnDTO();
                columnDTO.setID(resultSet.getString("Column.ID"));
                columnDTO.setTitle(resultSet.getString("Column.Title"));
                columnDTO.setWIP(resultSet.getInt("Column.WIP"));

                PreparedStatement cardStmt = connection.prepareStatement(
                        "SELECT `CardID` " +
                            "FROM `CardColumnMapper` " +
                            "WHERE `ColumnID`=?");
                cardStmt.setString(1, columnDTO.getID());

                ResultSet cardsResult = cardStmt.executeQuery();

                List<String> cardIDs = new ArrayList<>();
                while (cardsResult.next()) {
                    cardIDs.add(cardsResult.getString("CardID"));
                }

                columnDTO.setCardIDs(cardIDs);

                PreparedStatement preservedPositionStmt = connection.prepareStatement(
                        "SELECT `CardID` " +
                            "FROM `PreservedPositionMapper` " +
                            "WHERE `ColumnID`=?");
                preservedPositionStmt.setString(1, columnDTO.getID());

                ResultSet preservedPositionResult = preservedPositionStmt.executeQuery();

                List<String> preservedPosition = new ArrayList<>();
                while (cardsResult.next()) {
                    preservedPosition.add(cardsResult.getString("CardID"));
                }

                columnDTO.setPreservedPosition(preservedPosition);

                columnDTOList.add(columnDTO);
            }
            boardDTO.setColumnDTOs(columnDTOList);
            return boardDTO;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void insertColumn(Connection connection, ColumnDTO columnDTO, String boardID, int index) throws SQLException {
        PreparedStatement columnStmt = connection.prepareStatement(
                "INSERT INTO `Column`(`ID`, `Title`, `WIP`, `BoardID`, `Position`) " +
                        "VALUES (?, ?, ?, ?, ?)");
        columnStmt.setString(1, columnDTO.getID());
        columnStmt.setString(2, columnDTO.getTitle());
        columnStmt.setInt(3, columnDTO.getWIP());
        columnStmt.setString(4, boardID);
        columnStmt.setInt(5, index);
        columnStmt.executeUpdate();
    }
}
