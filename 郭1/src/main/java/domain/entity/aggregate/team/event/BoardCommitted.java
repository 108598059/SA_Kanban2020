package domain.entity.aggregate.team.event;

import domain.entity.DomainEvent;

public class BoardCommitted implements DomainEvent {
    private String teamId;
    private String boardId;
    public BoardCommitted(String teamId, String boardId){
        this.teamId = teamId;
        this.boardId = boardId;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getBoardId() {
        return boardId;
    }
}
