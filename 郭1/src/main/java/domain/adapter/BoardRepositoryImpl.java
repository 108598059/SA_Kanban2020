package domain.adapter;

import domain.entity.board.Board;
import domain.usecase.board.BoardRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BoardRepositoryImpl implements BoardRepository {
    private Map<String, Board> boardRepository ;
    private Connection conn;

    public BoardRepositoryImpl(){
        boardRepository = new HashMap<String, Board>() ;

    }


    public Board getBoardById(String id){
        return this.boardRepository.get(id) ;
    }

    public void save(Board board){
        try {
            this.boardRepository.put(board.getId(), board);
            this.conn = DatabaseImpl.getConnection();
            add(board);
            conn.close();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void add(Board board){

        this.boardRepository.put(board.getId(),board);

        String sql = "INSERT INTO kanban.board(id, name) VALUES (?,?) ON DUPLICATE KEY UPDATE name = ? ";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,board.getId());
            ps.setString(2,board.getName());
            ps.setString(3,board.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
