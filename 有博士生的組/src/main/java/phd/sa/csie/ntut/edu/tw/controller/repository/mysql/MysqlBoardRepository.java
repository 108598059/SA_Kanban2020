package phd.sa.csie.ntut.edu.tw.controller.repository.mysql;

import phd.sa.csie.ntut.edu.tw.controller.database.DB_connector;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.column.dto.ColumnDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlBoardRepository extends BoardRepository {
    @Override
    public void save(BoardDTO boardDto) {
        try {
            Connection connection = DB_connector.getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Board VALUES(?, ?)");
            stmt.setString(1, boardDto.getId());
            stmt.setString(2, boardDto.getName());

            stmt.executeUpdate();

            List<ColumnDTO> columnList = boardDto.getColumnDTOs();
            for (ColumnDTO columnDTO: columnList) {
                PreparedStatement columnStmt = connection.prepareStatement("INSERT INTO `Column`(`ID`, `Title`, `WIP`, `BoardID`, `Position`) VALUES (?, ?, ?, ?, ?)");
                columnStmt.setString(1, columnDTO.getId());
                columnStmt.setString(2, columnDTO.getTitle());
                columnStmt.setInt(3, columnDTO.getWip());
                columnStmt.setString(4, boardDto.getId());
                columnStmt.setInt(5, columnList.indexOf(columnDTO));
                columnStmt.executeUpdate();
            }

            DB_connector.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public BoardDTO findById(String id) {
        try {
            Connection connection = DB_connector.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM `Board`, `Column`, `Card` WHERE `Board`.`ID`=? AND`Column`.`BoardID`=`Board`.`ID` ORDER BY `Column`.`Position` ASC");
            stmt.setString(1, id);
            ResultSet resultSet = stmt.executeQuery();
            BoardDTO boardDTO = new BoardDTO();
            List<ColumnDTO> columnDTOList = new ArrayList<>();
            while (resultSet.next()) {
                if (boardDTO.getId() == null || boardDTO.getId().isEmpty()) {
                    boardDTO.setId(resultSet.getString("Board.ID"));
                    boardDTO.setName(resultSet.getString("Board.Name"));
                }
                ColumnDTO columnDTO = new ColumnDTO();
                columnDTO.setId(resultSet.getString("Column.ID"));
                columnDTO.setTitle(resultSet.getString("Column.Title"));
                columnDTO.setWip(resultSet.getInt("Column.WIP"));

                PreparedStatement cardStmt = connection.prepareStatement("SELECT `Card`.`ID` FROM `Column`, `Card` WHERE `Column`.`ID`=? AND`Card`.`ColumnID`=`Column`.`ID`");
                cardStmt.setString(1, columnDTO.getId());
                ResultSet cardsResult = cardStmt.executeQuery();

                List<String> cardIDs = new ArrayList<>();
                while (cardsResult.next()) {
                    cardIDs.add(cardsResult.getString("ID"));
                }
                stmt.setString(1, id);
                columnDTO.setCardIds(cardIDs);
                columnDTOList.add(columnDTO);


            }
            boardDTO.setColumnDTOs(columnDTOList);
            return boardDTO;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
