package domain.usecase.team.commit;

import domain.entity.DomainEventBus;
import domain.entity.aggregate.team.Team;
import domain.usecase.team.TeamRepository;
import domain.usecase.team.TeamTransformer;

public class CommitBoardUseCase {
    private TeamRepository teamRepository;
    private DomainEventBus eventBus;
    public CommitBoardUseCase(TeamRepository teamRepository, DomainEventBus eventBus) {
        this.teamRepository = teamRepository;
        this.eventBus = eventBus;
    }

    public void execute(CommitBoardInput input, CommitBoardOutput output){
        Team team = TeamTransformer.toTeam(teamRepository.getTeamById(input.getTeamId()));
        team.commitBoard(input.getBoardId());
        eventBus.postAll(team);
        teamRepository.save(TeamTransformer.toDTO(team));
        output.setBoardId(input.getBoardId());
    }

}
