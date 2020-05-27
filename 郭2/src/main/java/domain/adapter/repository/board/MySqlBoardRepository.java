package domain.adapter.repository.board;

import domain.adapter.database.DbConn;
import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.board.BoardEntity;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Default;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Alternative
@Singleton
public class MySqlBoardRepository implements IBoardRepository {
    private Connection conn;

    public MySqlBoardRepository() {
        conn = DbConn.getConnection();
    }

    public void add(BoardEntity board) {
        String sql = "INSERT INTO kanban.board(board_id,board_name) VALUES (?,?)";
        PreparedStatement ps = null;
        try {
            for(String workflowId : board.getWorkflowIds()) {
                addBoardToWorkflow(workflowId, board.getBoardId());
            }
            ps = conn.prepareStatement(sql);
            ps.setString(1,board.getBoardId());
            ps.setString(2,board.getBoardName());
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

    public BoardEntity getBoardById(String boardId) {
        String sql = "SELECT * FROM kanban.board WHERE board_id = '" + boardId + "'";
        BoardEntity board = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        try {
            ps = conn.prepareStatement(sql);
            rset = ps.executeQuery();
            while (rset.next()) {
                board = new BoardEntity();
                board.setBoardName(rset.getString("board_name"));
                board.setBoardId(boardId);
                board.setWorkflowIds(getWorkflowIdsByBoardId(boardId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rset != null) {
                try {
                    rset.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return board;
    }

    public void save(BoardEntity board) {
        String sql = "Insert Into kanban.board Values (? , ?) On Duplicate Key Update board_name= ?";
        PreparedStatement ps = null;
        try {

            for(String workflowId : board.getWorkflowIds()) {
                addBoardToWorkflow(workflowId, board.getBoardId());
            }

            ps = conn.prepareStatement(sql);
            ps.setString(1,board.getBoardId());
            ps.setString(2,board.getBoardName());
            ps.setString(3,board.getBoardName());
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

    @Override
    public List<BoardEntity> getAllBoard() {
        return null;
    }

    private List<String> getWorkflowIdsByBoardId(String boardId) {
        PreparedStatement ps = null;
        ResultSet rset = null;
        List<String> workflowIds = new ArrayList<String>();
        try {
            String query = "Select * From kanban.board_to_workflow Where board_id = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1,boardId);
            rset = ps.executeQuery();
            while (rset.next()) {
                String workFlowId = rset.getString("workflow_id");
                workflowIds.add(workFlowId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rset != null) {
                try {
                    rset.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return workflowIds;
    }

    private void addBoardToWorkflow(String workflowId, String boardId) {
        String sql = "Insert Ignore Into kanban.board_to_workflow Values (?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,boardId);
            ps.setString(2,workflowId);
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
