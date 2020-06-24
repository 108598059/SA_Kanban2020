package domain.adapters.repository;

import domain.adapters.database.Database;
import domain.usecase.team.TeamDTO;
import domain.usecase.team.TeamRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamRepositoryImpl implements TeamRepository {

    private Connection conn;
    private List<TeamDTO> teamList;
    public TeamRepositoryImpl(){
        teamList = new ArrayList<TeamDTO>();
    }


    public TeamDTO getTeamById(String id){
        for(TeamDTO teamDTO : teamList){
            if(teamDTO.getId().equals(id)) return teamDTO;
        }
        return null;
    }

    public void save(TeamDTO teamDTO){


        String sql = "INSERT INTO kanban.team(id, name) VALUES (?,?) ON DUPLICATE KEY UPDATE name=?";
        PreparedStatement ps = null;
        this.conn = Database.getConnection();

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,teamDTO.getId());
            ps.setString(2,teamDTO.getName());
            ps.setString(3,teamDTO.getName());
            ps.executeUpdate() ;
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



        sql = "INSERT INTO kanban.r_team_board(teamid, boardid) VALUES (?,?) ON DUPLICATE KEY UPDATE teamid = ?,boardid = ? ";
        try {
            ps = conn.prepareStatement(sql);
            for (String boardid : teamDTO.getBoardIdList()){
                System.out.println("底家" + boardid);

                ps.setString(1,teamDTO.getId());
                ps.setString(2,boardid);
                ps.setString(3,teamDTO.getId());
                ps.setString(4,boardid);
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

    public void add(TeamDTO teamDTO){


        String sql = "INSERT INTO kanban.team(id, name) VALUES (?,?)";
        PreparedStatement ps = null;
        this.conn = Database.getConnection();

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,teamDTO.getId());
            ps.setString(2,teamDTO.getName());
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

        teamList.add(teamDTO);
    }

}
