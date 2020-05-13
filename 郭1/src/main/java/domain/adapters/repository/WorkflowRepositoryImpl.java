package domain.adapters.repository;

import domain.adapters.database.Database;
import domain.entity.workflow.Stage;
import domain.entity.workflow.Swimlane;
import domain.entity.workflow.Workflow;
import domain.usecase.workflow.WorkflowRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkflowRepositoryImpl implements WorkflowRepository {

    private Connection conn;

    public WorkflowRepositoryImpl(){

    }

    public Workflow getWorkFlowById(String id){

        String sql;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Workflow workflow = null;


        try {
            this.conn = Database.getConnection();

            sql = "SELECT * FROM kanban.workflow WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            resultSet = ps.executeQuery();
            resultSet.first();

            workflow = new Workflow();
            workflow.setId(resultSet.getString("id"));
            workflow.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }



        try {

            sql = "SELECT * FROM kanban.stage WHERE workflowid=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,workflow.getId());
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Stage stage = new Stage();
                stage.setId(resultSet.getString("id"));
                stage.setName(resultSet.getString("name"));
                workflow.addStage(stage);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {

            sql = "SELECT * FROM kanban.swimlane WHERE stageid=?";
            ps = conn.prepareStatement(sql);

            for (Stage stage: workflow.getStageMap().values()) {

                ps.setString(1, stage.getId());
                resultSet = ps.executeQuery();

                while (resultSet.next()) {
                    Swimlane swimlane = new Swimlane();
                    swimlane.setId(resultSet.getString("id"));
                    swimlane.setName(resultSet.getString("name"));
                    stage.addSwimlane(swimlane);

                }
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

        }

        try {

            sql = "SELECT * FROM kanban.swimlane k LEFT JOIN kanban.r_swimlane_card r ON k.id=r.swimlaneid";
            ps = conn.prepareStatement(sql);


            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                workflow.addCard(resultSet.getString("stageid"),
                        resultSet.getString("swimlaneid"),
                        resultSet.getString("cardid"));
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



        return workflow;
    }

    public void save(Workflow workflow) {
        String sql;



        this.conn = Database.getConnection();


        PreparedStatement ps = null;
        sql = "INSERT INTO kanban.stage (id,name,workflowid) VALUES (?,?,?) ON DUPLICATE KEY UPDATE name=?,workflowid=?";
        try {
            for(Stage stage : workflow.getStageMap().values() ){
                ps = conn.prepareStatement(sql);
                ps.setString(1,stage.getId());
                ps.setString(2,stage.getName());
                ps.setString(3,workflow.getId());
                ps.setString(4,stage.getName());
                ps.setString(5,workflow.getId());
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
        }


        sql = "INSERT INTO kanban.swimlane (id,name,stageid) VALUES (?,?,?) ON DUPLICATE KEY UPDATE name=?,stageid=?";
        try {
            for(Stage stage : workflow.getStageMap().values() ){
                for(Swimlane swimlane : stage.getSwimlaneMap().values()){
                    ps = conn.prepareStatement(sql);
                    ps.setString(1,swimlane.getId());
                    ps.setString(2,swimlane.getName());
                    ps.setString(3,stage.getId());
                    ps.setString(4,swimlane.getName());
                    ps.setString(5,stage.getId());
                    ps.executeUpdate();
                }
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

        }

        sql = "INSERT INTO kanban.r_swimlane_card (swimlaneid,cardid) VALUES (?,?) ON DUPLICATE KEY UPDATE swimlaneid=?,cardid=?";
        try {
            for(Stage stage : workflow.getStageMap().values() ){
                for(Swimlane swimlane : stage.getSwimlaneMap().values()){
                    for (String cardId: swimlane.getCard()) {

                        ps = conn.prepareStatement(sql);
                        ps.setString(1, swimlane.getId());
                        ps.setString(2, cardId);
                        ps.setString(3, swimlane.getId());
                        ps.setString(4, cardId);



                        ps.executeUpdate();
                    }
                }
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


    public void add(Workflow workflow){
        //save workflow

        String sql = "INSERT INTO kanban.workflow(id, name) VALUES (?,?)";
        PreparedStatement ps = null;
        try {
            this.conn = Database.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1,workflow.getId());
            ps.setString(2,workflow.getName());
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
