package domain.entity.aggregate.team;

import domain.entity.aggregate.Aggregate;
import domain.entity.aggregate.team.event.BoardCommitted;
import domain.entity.aggregate.team.event.TeamCreated;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Team extends Aggregate {
    private String id ;
    private String name ;
    private List<String> userIdList;
    private List<String> boardIdList;

    public Team(String teamName){
        userIdList = new ArrayList<String>();
        boardIdList = new ArrayList<String>();
        this.id = UUID.randomUUID().toString();
        this.name = teamName;
        addEvent(new TeamCreated(this.id, this.name));
    }

    public Team(String teamId, String teamName, List<String> userIdList, List<String> boardIdList){
        this.userIdList = userIdList;
        this.boardIdList = boardIdList;
        id = teamId;
        this.name = teamName;
    }

    public void commitBoard(String boardId){
        for(String id : boardIdList){
            // check if board already exist, if true do nothing
            if(id.equals(boardId)) return;
        }
        addEvent(new BoardCommitted(boardId, this.id));
        boardIdList.add(boardId);
    }
    public void addUser(String userId){
        for(String id : userIdList){
            if(id.equals(userId)) return;
        }
        userIdList.add(userId);
    }
    public boolean isContainBoard(String boardId){
        for(String id : boardIdList){
            if(id.equals(boardId)) return true;
        }
        return false;
    }
    public boolean isContainUser(String userId){
        for(String id : userIdList){
            if(id.equals(userId)) return true;
        }
        return false;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public List<String> getBoardIdList() {
        return boardIdList;
    }

    public void setBoardIdList(List<String> boardIdList) {
        this.boardIdList = boardIdList;
    }
}
