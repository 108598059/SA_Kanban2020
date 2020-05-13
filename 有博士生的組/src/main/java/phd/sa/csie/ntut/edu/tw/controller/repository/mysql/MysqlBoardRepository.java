package phd.sa.csie.ntut.edu.tw.controller.repository.mysql;

import phd.sa.csie.ntut.edu.tw.controller.database.DB_connector;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class MysqlBoardRepository extends BoardRepository {
    @Override
    public void save(BoardDTO boardDto) {
        try {
            Connection connection = DB_connector.getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Board VALUES(?, ?)");
            stmt.setString(1, boardDto.getId().toString());
            stmt.setString(2, boardDto.getName());

            stmt.executeUpdate();
            DB_connector.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public BoardDTO findById(UUID id) {
        return null;
    }
}
