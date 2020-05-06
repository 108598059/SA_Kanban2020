package domain.adapter;

import domain.entity.board.Board;
import domain.usecase.board.BoardDTO;
import domain.usecase.board.BoardRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardRepositoryImpl implements BoardRepository {
    private Map<String, Board> boardRepository ;
    private Connection conn;

    public BoardRepositoryImpl(){
        boardRepository = new HashMap<String, Board>() ;

    }


    public BoardDTO getBoardById(String id){
        //return this.boardRepository.get(id) ;

        String sql = "SELECT * FROM kanban.board b LEFT JOIN kanban.r_board_workflow r ON b.id = r.boardid WHERE b.id = ?";

        PreparedStatement ps = null;
        ResultSet resultSet = null;
        BoardDTO boardDTO = null;
        List<String> workflows = new ArrayList<String>();

        try {
            this.conn = DatabaseImpl.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1,id);

            resultSet = ps.executeQuery();
            resultSet.next();

            boardDTO = new BoardDTO();
            boardDTO.setId(id);
            boardDTO.setName(resultSet.getString("name"));


            do {
                if(resultSet.getString("workflowid") != null)
                    workflows.add(resultSet.getString("workflowid"));

            }while (resultSet.next());

            boardDTO.setWorkflows(workflows);

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
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return boardDTO;
    }

    public void save(BoardDTO boardDTO){

            //this.boardRepository.put(boardDTO.getId(), boardDTO);
        String sql = "INSERT INTO kanban.board(id, name) VALUES (?,?) ON DUPLICATE KEY UPDATE name = ? ";
        PreparedStatement ps = null;

        try {
            this.conn = DatabaseImpl.getConnection();

            ps = conn.prepareStatement(sql);
            ps.setString(1,boardDTO.getId());
            ps.setString(2,boardDTO.getName());
            ps.setString(3,boardDTO.getName());
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


        sql = "INSERT INTO kanban.r_board_workflow(boardid, workflowid) VALUES (?,?) ON DUPLICATE KEY UPDATE boardid = ?,workflowid = ? ";
        try {
            ps = conn.prepareStatement(sql);

            for (String workflowid : boardDTO.getWorkflows()){
                ps.setString(1,boardDTO.getId());
                ps.setString(2,workflowid);
                ps.setString(3,boardDTO.getId());
                ps.setString(4,workflowid);
                ps.executeUpdate();
            }



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
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void add(BoardDTO boardDTO){

        //this.boardRepository.put(boardDTO.getId(),boardDTO);

        String sql = "INSERT INTO kanban.board(id, name) VALUES (?,?)";
        PreparedStatement ps = null;

        try {
            this.conn = DatabaseImpl.getConnection();

            ps = conn.prepareStatement(sql);
            ps.setString(1,boardDTO.getId());
            ps.setString(2,boardDTO.getName());
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
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
