package domain.usecase.team;


import domain.entity.aggregate.team.Team;

public class TeamTransformer {

    public static TeamDTO toDTO(Team team){
        TeamDTO teamDTO = new TeamDTO(team);

        System.out.println("inside transformer toDTO : " + teamDTO.getId());
        return teamDTO;
    }

    public static Team toTeam(TeamDTO teamDTO){
        return new Team(
                teamDTO.getId(),
                teamDTO.getName(),
                teamDTO.getUserIdList(),
                teamDTO.getBoardIdList());
    }
}
