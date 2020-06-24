package domain.usecase.team.commit;

public interface CommitBoardInput {
    public void setBoardId(String boardId);
    public String getBoardId();

    public void setTeamId(String teamId);
    public String getTeamId();

}
