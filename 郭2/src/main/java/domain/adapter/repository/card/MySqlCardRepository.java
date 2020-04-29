package domain.adapter.repository.card;

import domain.adapter.database.DbConn;
import domain.aggregate.card.Card;
import domain.aggregate.card.Task;
import domain.usecase.card.repository.ICardRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCardRepository implements ICardRepository {
    private Connection conn;

    public MySqlCardRepository() {
        conn = DbConn.getConnection();
    }

    public void add(Card card) {
        String sql = "INSERT INTO kanban.card(card_id, card_name, card_content, card_type) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            for(Task Task : card.getTaskList()) {
                addTask(Task);
            }
            ps = conn.prepareStatement(sql);
            ps.setString(1,card.getCardId());
            ps.setString(2,card.getCardName());
            ps.setString(3,card.getCardContent());
            ps.setString(4,card.getCardType());
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

    private void addTask(Task task) {
        String sql = "Insert Into kanban.task Values (?, ?, ?, ?) On Duplicate Key Update task_name = ? ";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, task.getTaskId());
            ps.setString(2, task.getCardId());
            ps.setString(3, task.getTaskName());
            ps.setString(4, task.getTaskContent());
            ps.setString(5, task.getTaskName());
//            ps.setString(6, task.getTaskContent());
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

    public Card getCardById(String cardId) {
        String sql = "SELECT * FROM kanban.card WHERE card_id = '" + cardId + "'";
        Card card = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        try {
            ps = conn.prepareStatement(sql);
            rset = ps.executeQuery();
            while (rset.next()) {
                card = new Card(rset.getString("card_name"));
                card.setCardId(cardId);
                card.setCardContent(rset.getString("card_content"));
                card.setCardType(rset.getString("card_type"));
                card.setTaskList(getTaskListByCardId(cardId));
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
        return card;
    }

    public void save(Card card) {
        String sql = "Insert Into kanban.card Values (?, ?, ?, ?) On Duplicate Key Update card_name=? ";
        PreparedStatement ps = null;
        try {
            for(Task Task : card.getTaskList()) {
                addTask(Task);
            }
            ps = conn.prepareStatement(sql);
            ps.setString(1,card.getCardId());
            ps.setString(2,card.getCardName());
            ps.setString(3,card.getCardContent());
            ps.setString(4,card.getCardType());
            ps.setString(5,card.getCardName());
//            ps.setString(6,card.getCardContent());
//            ps.setString(7,card.getCardType());
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

    private List<Task> getTaskListByCardId(String cardId) {
        List<Task> taskList = new ArrayList<Task>();
        String sql = "SELECT * FROM kanban.task WHERE card_id = '" + cardId + "'";
        PreparedStatement ps = null;
        ResultSet rset = null;
        try {
            ps = conn.prepareStatement(sql);
            rset = ps.executeQuery();
            Task task = null;
            while (rset.next()) {
                task = new Task(cardId, rset.getString("task_name"));
                task.setTaskId(rset.getString("task_id"));
                taskList.add(task);
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
        return taskList;
    }
}
