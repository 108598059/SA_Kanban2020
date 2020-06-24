package domain.usecase.team;


public interface TeamRepository {

    TeamDTO getTeamById(String id);
    void add(TeamDTO teamDTO);
    void save(TeamDTO teamDTO);
}
