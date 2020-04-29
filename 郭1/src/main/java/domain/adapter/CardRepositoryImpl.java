package domain.adapter;

import domain.entity.card.Card;
import domain.entity.card.Task;
import domain.usecase.card.CardRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CardRepositoryImpl implements CardRepository {
    private HashMap<String, Card> _cardRepository ;
    private Connection conn;

    public CardRepositoryImpl(){
        _cardRepository = new HashMap<String, Card>() ;
    }

    public Card get(String id){
        String sql;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Card card= null;

        try {
            this.conn = DatabaseImpl.getConnection();

            sql = "SELECT * FROM kanban.card WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            resultSet = ps.executeQuery();
            resultSet.first();

            card = new Card();
            card.setId(resultSet.getString("id"));
            card.setName(resultSet.getString("name"));

        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {

            sql = "SELECT * FROM kanban.subtask WHERE cardid=?";
            ps = conn.prepareStatement(sql);

            ps.setString(1,card.getId());
            resultSet = ps.executeQuery();

            while(resultSet.next()){
                Task task = new Task();
                task.setId(resultSet.getString("id"));
                task.setName(resultSet.getString("name"));
                card.addTask(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null){
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

        return card;
    }

    public void save(Card card){

        String sql = "INSERT INTO kanban.subtask(id, name, cardid) VALUES (?,?,?) ON DUPLICATE KEY UPDATE name=?,cardid=?";
        PreparedStatement ps = null;
        this.conn = DatabaseImpl.getConnection();

        try {
            ps = conn.prepareStatement(sql);

            for(Task task: card.getTaskMap().values())
            {

                ps.setString(1, task.getId());
                ps.setString(2, task.getName());
                ps.setString(3, card.getId());
                ps.setString(4, task.getName());
                ps.setString(5, card.getId());
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
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }



    public void add(Card card){
        //save card
        String sql = "INSERT INTO kanban.card(id, name) VALUES (?,?)";
        PreparedStatement ps = null;
        this.conn = DatabaseImpl.getConnection();

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,card.getId());
            ps.setString(2,card.getName());
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
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
