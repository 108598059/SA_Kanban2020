package domain.adapters.repository;

import domain.adapters.database.Database;
import domain.usecase.workflow.StageDTO;
import domain.usecase.workflow.SwimlaneDTO;
import domain.usecase.workflow.WorkflowDTO;
import domain.usecase.workflow.WorkflowRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkflowRepositoryImpl implements WorkflowRepository {

    private Connection conn;

    public WorkflowRepositoryImpl(){

    }

    public WorkflowDTO getWorkFlowById(String id){

        String sql;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        WorkflowDTO workflowDTO = null;


        try {
            this.conn = Database.getConnection();

            sql = "SELECT * FROM kanban.workflow WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            resultSet = ps.executeQuery();
            resultSet.first();

            workflowDTO = new WorkflowDTO(resultSet.getString("id"));

            workflowDTO.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }



        try {

            sql = "SELECT * FROM kanban.stage WHERE workflowid=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,workflowDTO.getId());
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                StageDTO stageDTO = new StageDTO();
                stageDTO.setId(resultSet.getString("id"));
                stageDTO.setName(resultSet.getString("name"));
                workflowDTO.addStage(stageDTO);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {

            sql = "SELECT * FROM kanban.swimlane WHERE stageid=?";
            ps = conn.prepareStatement(sql);

            for (StageDTO stageDTO: workflowDTO.getStages().values()) {

                ps.setString(1, stageDTO.getId());
                resultSet = ps.executeQuery();

                while (resultSet.next()) {
                    SwimlaneDTO swimlaneDTO = new SwimlaneDTO();
                    swimlaneDTO.setId(resultSet.getString("id"));
                    swimlaneDTO.setName(resultSet.getString("name"));
                    stageDTO.addSwimlane(swimlaneDTO);

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
                workflowDTO.addCard(resultSet.getString("stageid"),
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


        return workflowDTO;
    }

    public void save(WorkflowDTO workflowDTO) {

        delete(workflowDTO);

        String sql;
        this.conn = Database.getConnection();


        PreparedStatement ps = null;
        sql = "INSERT INTO kanban.stage (id,name,workflowid) VALUES (?,?,?) ON DUPLICATE KEY UPDATE name=?,workflowid=?";
        try {
            for(StageDTO stageDTO : workflowDTO.getStages().values() ){
                ps = conn.prepareStatement(sql);
                ps.setString(1,stageDTO.getId());
                ps.setString(2,stageDTO.getName());
                ps.setString(3,workflowDTO.getId());
                ps.setString(4,stageDTO.getName());
                ps.setString(5,workflowDTO.getId());
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
            for(StageDTO stageDTO : workflowDTO.getStages().values() ){
                for(SwimlaneDTO swimlaneDTO : stageDTO.getSwimlanes().values()){
                    ps = conn.prepareStatement(sql);
                    ps.setString(1,swimlaneDTO.getId());
                    ps.setString(2,swimlaneDTO.getName());
                    ps.setString(3,stageDTO.getId());
                    ps.setString(4,swimlaneDTO.getName());
                    ps.setString(5,stageDTO.getId());
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
            for(StageDTO stageDTO : workflowDTO.getStages().values() ){
                for(SwimlaneDTO swimlaneDTO : stageDTO.getSwimlanes().values()){
                    for (String cardId: swimlaneDTO.getCards()) {

                        ps = conn.prepareStatement(sql);
                        ps.setString(1, swimlaneDTO.getId());
                        ps.setString(2, cardId);
                        ps.setString(3, swimlaneDTO.getId());
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


    public void add(WorkflowDTO workflow){
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

    public void delete(WorkflowDTO workflowDTO){
        String sql;

        this.conn = Database.getConnection();

        PreparedStatement ps = null;
        sql = "DELETE FROM kanban.stage WHERE workflowid=?";
        try {

                ps = conn.prepareStatement(sql);
                ps.setString(1, workflowDTO.getId());
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


        sql = "DELETE FROM kanban.swimlane WHERE stageid=?";
        try {
                for(StageDTO stageDTO : workflowDTO.getStages().values() ){
                    ps = conn.prepareStatement(sql);
                    ps.setString(1,stageDTO.getId());
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

        sql = "DELETE FROM kanban.r_swimlane_card WHERE swimlaneid=?";
        try {
            for(StageDTO stageDTO : workflowDTO.getStages().values() ){
                for(SwimlaneDTO swimlaneDTO : stageDTO.getSwimlanes().values()){

                        ps = conn.prepareStatement(sql);
                        ps.setString(1, swimlaneDTO.getId());
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
