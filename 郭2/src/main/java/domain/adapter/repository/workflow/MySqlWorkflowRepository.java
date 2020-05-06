package domain.adapter.repository.workflow;

import domain.adapter.database.DbConn;
import domain.model.aggregate.workflow.Lane;
import domain.model.aggregate.workflow.Stage;
import domain.model.aggregate.workflow.Swimlane;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.workflow.repository.IWorkflowRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlWorkflowRepository implements IWorkflowRepository {
    private Connection conn;

    public MySqlWorkflowRepository() {
         conn = DbConn.getConnection();
    }

    public void add(Workflow workflow) {
        String sql = "INSERT INTO kanban.workflow(workflow_id,workflow_name) VALUES (?,?)";
        PreparedStatement ps = null;
        try {
            for(Lane lane : workflow.getLaneList()) {
                addLane(lane);
                for(String cardId : lane.getCardIdList()){
                    addLaneToCard(lane.getLaneId(), cardId);
                }
            }
            ps = conn.prepareStatement(sql);
            ps.setString(1,workflow.getWorkflowId());
            ps.setString(2,workflow.getWorkflowName());
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

    private void addLaneToCard(String laneId, String cardId) {
        String sql = "Insert Ignore Into kanban.lane_to_card Values (?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,laneId);
            ps.setString(2,cardId);
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

    public Workflow getWorkflowById(String workflowId) {
        String sql = "SELECT * FROM kanban.workflow WHERE workflow_id = '" + workflowId + "'";
        Workflow workflow = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        try {
            ps = conn.prepareStatement(sql);
            rset = ps.executeQuery();
            if(rset.first()) {
                workflow = new Workflow(rset.getString("workflow_name"));
                workflow.setWorkflowId(workflowId);
                workflow.setLaneList(getLaneListByWorkflowId(workflowId));
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
        return workflow;
    }

    public void save(Workflow workflow) {
        String sql = "Insert Into kanban.workflow Values (? , ?) On Duplicate Key Update workflow_name= ?";
        PreparedStatement ps = null;
        try {
            for(Lane lane : workflow.getLaneList()) {
                addLane(lane);
                for(String cardId : lane.getCardIdList()){
                    addLaneToCard(lane.getLaneId(), cardId);
                }
            }
            ps = conn.prepareStatement(sql);
            ps.setString(1,workflow.getWorkflowId());
            ps.setString(2,workflow.getWorkflowName());
            ps.setString(3,workflow.getWorkflowName());
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

    private void addLane(Lane lane) throws SQLException {
        String sql = "Insert Into kanban.lane Values (?, ?, ?, ?) On Duplicate Key Update lane_name= ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, lane.getLaneId());
            ps.setString(2, lane.getWorkflowId());
            ps.setString(3, lane.getLaneName());
            ps.setString(4, lane.getLaneDirection());
            ps.setString(5, lane.getLaneName());
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

    private List<Lane> getLaneListByWorkflowId(String workflowId) {
        List<Lane> laneList = new ArrayList<Lane>();
        String sql = "SELECT * FROM kanban.lane WHERE workflow_id = '" + workflowId + "'";
        PreparedStatement ps = null;
        ResultSet rset = null;
        try {
            ps = conn.prepareStatement(sql);
            rset = ps.executeQuery();
            Lane lane = null;
            while (rset.next()) {
                if(rset.getString("lane_direction").equals("VERTICAL")){
                    lane = new Stage(rset.getString("lane_name"), workflowId);
                }
                else if(rset.getString("lane_direction").equals("HORIZONTAL")){
                    lane = new Swimlane(rset.getString("lane_name"), workflowId);
                }
                lane.setLaneId(rset.getString("lane_id"));
                lane.setCardIdList(getCardIdListByLaneId(rset.getString("lane_id")));
                laneList.add(lane);
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
        return laneList;
    }

    private List<String> getCardIdListByLaneId(String laneId) {
        List<String> cardIdList = new ArrayList<String>();
        String sql = "SELECT * FROM kanban.lane_to_card WHERE lane_id = '" + laneId + "'";
        PreparedStatement ps = null;
        ResultSet rset = null;
        try {
            ps = conn.prepareStatement(sql);
            rset = ps.executeQuery();
            Lane lane = null;
            while (rset.next()) {
                cardIdList.add(rset.getString("card_id"));
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
        return cardIdList;
    }
}
