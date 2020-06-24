package domain.usecase.board.create;

public interface CreateBoardInput {
    public void setName(String name);
    public String getName();

    public void setTeamId(String teamId);
    public String getTeamId();
}
