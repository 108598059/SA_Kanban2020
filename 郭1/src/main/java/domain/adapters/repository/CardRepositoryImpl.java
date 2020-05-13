package domain.adapters.repository;

import domain.adapters.database.Database;
import domain.entity.card.Card;
import domain.entity.card.Subtask;
import domain.usecase.card.CardRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardRepositoryImpl implements CardRepository {

    private Connection conn;

    public CardRepositoryImpl(){

    }

    public Card getCardById(String id){
        String sql;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Card card= null;

        try {
            this.conn = Database.getConnection();

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
                Subtask subtask = new Subtask();
                subtask.setId(resultSet.getString("id"));
                subtask.setName(resultSet.getString("name"));
                card.addSubtask(subtask);
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
        this.conn = Database.getConnection();

        try {
            ps = conn.prepareStatement(sql);

            for(Subtask subtask : card.getTaskMap().values())
            {

                ps.setString(1, subtask.getId());
                ps.setString(2, subtask.getName());
                ps.setString(3, card.getId());
                ps.setString(4, subtask.getName());
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
        this.conn = Database.getConnection();

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
