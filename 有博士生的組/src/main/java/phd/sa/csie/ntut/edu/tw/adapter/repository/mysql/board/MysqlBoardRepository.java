package phd.sa.csie.ntut.edu.tw.adapter.repository.mysql.board;

import phd.sa.csie.ntut.edu.tw.adapter.database.DB_connector;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.column.dto.ColumnDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;

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

            List<ColumnDTO> columnList = boardDTO.getColumnDTOs();
            for (ColumnDTO columnDTO: columnList) {
                PreparedStatement columnStmt = connection.prepareStatement(
                        "INSERT INTO `Column`(`ID`, `Title`, `WIP`, `BoardID`, `Position`) " +
                            "VALUES (?, ?, ?, ?, ?)");
                columnStmt.setString(1, columnDTO.getID());
                columnStmt.setString(2, columnDTO.getTitle());
                columnStmt.setInt(3, columnDTO.getWIP());
                columnStmt.setString(4, boardDTO.getID());
                columnStmt.setInt(5, columnList.indexOf(columnDTO));
                columnStmt.executeUpdate();
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

            List<ColumnDTO> columnList = boardDTO.getColumnDTOs();
            for (ColumnDTO columnDTO: columnList) {
                PreparedStatement columnStmt = connection.prepareStatement(
                        "UPDATE `Column` " +
                            "SET `Title`=?,`WIP`=?,`BoardID`=?,`Position`=? " +
                            "WHERE `ID`=?");
                columnStmt.setString(1, columnDTO.getTitle());
                columnStmt.setInt(2, columnDTO.getWIP());
                columnStmt.setString(3, boardDTO.getID());
                columnStmt.setInt(4, columnList.indexOf(columnDTO));
                columnStmt.setString(5, columnDTO.getID());;
                columnStmt.executeUpdate();
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

            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM `Board`, `Column` " +
                        "WHERE `Board`.`ID`=? AND`Column`.`BoardID`=`Board`.`ID` " +
                        "ORDER BY `Column`.`Position` ASC");
            stmt.setString(1, id);

            ResultSet resultSet = stmt.executeQuery();

            BoardDTO boardDTO = new BoardDTO();
            List<ColumnDTO> columnDTOList = new ArrayList<>();
            while (resultSet.next()) {
                if (boardDTO.getID() == null || boardDTO.getID().isEmpty()) {
                    boardDTO.setID(resultSet.getString("Board.ID"));
                    boardDTO.setName(resultSet.getString("Board.Name"));
                    boardDTO.setWorkspaceID(resultSet.getString("Board.WorkspaceID"));
                }

                ColumnDTO columnDTO = new ColumnDTO();
                columnDTO.setID(resultSet.getString("Column.ID"));
                columnDTO.setTitle(resultSet.getString("Column.Title"));
                columnDTO.setWIP(resultSet.getInt("Column.WIP"));

                PreparedStatement cardStmt = connection.prepareStatement(
                        "SELECT `Card`.`ID` " +
                            "FROM `Column`, `Card` " +
                            "WHERE `Column`.`ID`=? AND`Card`.`ColumnID`=`Column`.`ID`");
                cardStmt.setString(1, columnDTO.getID());

                ResultSet cardsResult = cardStmt.executeQuery();

                List<String> cardIDs = new ArrayList<>();
                while (cardsResult.next()) {
                    cardIDs.add(cardsResult.getString("ID"));
                }
                stmt.setString(1, id);
                columnDTO.setCardIDs(cardIDs);
                columnDTOList.add(columnDTO);
            }
            boardDTO.setColumnDTOs(columnDTOList);
            return boardDTO;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
