package domain.entity.aggregate.team.event;

import domain.entity.DomainEvent;

public class TeamCreated implements DomainEvent {
    private String teamId ;
    private String teamName ;

    public TeamCreated(String teamId, String teamName ) {
        this.teamId = teamId ;
        this.teamName = teamName ;
    }

    public String getBoardId() {
        return teamId;
    }

    public String getBoardName() {
        return teamName;
    }
}
