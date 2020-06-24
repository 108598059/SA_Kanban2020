package domain.entity.aggregate.board.event;

import domain.entity.DomainEvent;

public class BoardCreated implements DomainEvent {
    private String boardId ;
    private String commitToTeamId;
    private String boardName ;

    public BoardCreated( String boardId, String boardName, String commitToTeamId ) {
        this.boardId = boardId ;
        this.boardName = boardName ;
        this.commitToTeamId = commitToTeamId;
    }

    public String getBoardId() {
        return boardId;
    }
    public String getCommitToTeamId(){ return commitToTeamId; }
    public String getBoardName() {
        return boardName;
    }
}
