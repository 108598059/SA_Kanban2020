package domain.usecase.team;

import domain.entity.aggregate.team.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamDTO{
    private String id ;
    private String name ;
    private List<String> userIdList;
    private List<String> boardIdList;

    public TeamDTO(String teamName, String id){
        userIdList = new ArrayList<String>();
        boardIdList = new ArrayList<String>();
        this.name = teamName;
        this.id = id;

    }
    public TeamDTO(Team team){
        this.userIdList = team.getUserIdList();
        this.boardIdList = team.getBoardIdList();
        this.name = team.getName();
        this.id = team.getId();

    }



    public void addBoard(String boardId){
        for(String id : boardIdList){
            if(id.equals(boardId)) return;
        }
        boardIdList.add(boardId);
    }
    public void addUser(String userId){
        for(String id : userIdList){
            if(id.equals(userId)) return;
        }
        userIdList.add(userId);
    }

    public void setId(String id){
        this.id = this.id;
    }

    public String getId() {
        return id;
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
