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
import java.util.List;

public class MysqlBoardRepository extends BoardRepository {
    @Override
    public void save(BoardDTO boardDto) {
        try {
            Connection connection = DB_connector.getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Board VALUES(?, ?)");
            stmt.setString(1, boardDto.getId().toString());
            stmt.setString(2, boardDto.getName());

//            stmt.executeUpdate();

            List<ColumnDTO> columnList = boardDto.getColumnDTOs();
            for (ColumnDTO columnDTO: columnList) {
                PreparedStatement columnStmt = connection.prepareStatement("INSERT INTO Column VALUES(?, ?, ?, ?)");
                columnStmt.setString(1, columnDTO.getId());
                columnStmt.setString(2, columnDTO.getTitle());
                columnStmt.setInt(3, columnDTO.getWip());
                columnStmt.setString(4, boardDto.getId().toString());
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
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM `Board`, `Column`, `Card` WHERE `Board`.`ID`=? AND`Column`.`BoardID`=`Board`.`ID`");
            stmt.setString(1, id);
            ResultSet resultSet = stmt.executeQuery();
            BoardDTO boardDTO = new BoardDTO();
            while (resultSet.next()) {
                if (boardDTO.getId().isEmpty()) {
                    boardDTO.setId(resultSet.getString("`Board`.`ID"));
                    boardDTO.setName(resultSet.getString("`Board`.`Name`"));
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }
}
