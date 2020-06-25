package domain.adapters.repository;

import domain.adapters.database.Database;
import domain.usecase.card.CardDTO;
import domain.usecase.card.CardRepository;
import domain.usecase.card.SubtaskDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardRepositoryImpl implements CardRepository {

    private Connection conn;

    public CardRepositoryImpl(){

    }

    public CardDTO getCardById(String id){
        String sql;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        CardDTO card= null;

        try {
            this.conn = Database.getConnection();

            sql = "SELECT * FROM kanban.card WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            resultSet = ps.executeQuery();
            resultSet.first();

            card = new CardDTO(
                    resultSet.getString("id"),
                    resultSet.getString("name"));


        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {

            sql = "SELECT * FROM kanban.subtask WHERE cardid=?";
            ps = conn.prepareStatement(sql);

            ps.setString(1,card.getId());
            resultSet = ps.executeQuery();

            while(resultSet.next()){
                SubtaskDTO subtaskDTO = new SubtaskDTO();
                subtaskDTO.setId(resultSet.getString("id"));
                subtaskDTO.setName(resultSet.getString("name"));

                card.addSubtask(subtaskDTO);
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

    public void save(CardDTO card){

        String sql = "INSERT INTO kanban.subtask(id, name, cardid) VALUES (?,?,?) ON DUPLICATE KEY UPDATE name=?,cardid=?";
        PreparedStatement ps = null;
        this.conn = Database.getConnection();

        try {
            ps = conn.prepareStatement(sql);

            for(SubtaskDTO subtaskDTO : card.getSubtasks().values())
            {

                ps.setString(1, subtaskDTO.getId());
                ps.setString(2, subtaskDTO.getName());
                ps.setString(3, card.getId());
                ps.setString(4, subtaskDTO.getName());
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



    public void add(CardDTO card){
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
