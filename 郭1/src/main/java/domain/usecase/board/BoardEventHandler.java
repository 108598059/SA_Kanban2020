package domain.usecase.board;

import com.google.common.eventbus.Subscribe;

import domain.adapters.controller.team.CommitBoardInputImpl;
import domain.adapters.controller.team.CommitBoardOutputImpl;
import domain.entity.DomainEventBus;
import domain.entity.aggregate.board.event.BoardCreated;

import domain.usecase.team.commit.CommitBoardUseCase;
import domain.usecase.team.TeamRepository;
import domain.usecase.team.commit.CommitBoardInput;
import domain.usecase.team.commit.CommitBoardOutput;


public class BoardEventHandler {
    private DomainEventBus eventBus;
    private TeamRepository teamRepository;

    public BoardEventHandler(TeamRepository teamRepository, DomainEventBus eventBus) {
        this.teamRepository = teamRepository;
        this.eventBus = eventBus;
    }

    @Subscribe
    public void commitBoardHandleEvent(BoardCreated boardCreated){

        CommitBoardInput commitBoardInput = new CommitBoardInputImpl();
        CommitBoardOutput commitBoardOutput = new CommitBoardOutputImpl();
        CommitBoardUseCase commitBoardUseCase = new CommitBoardUseCase(teamRepository, eventBus);

        commitBoardInput.setBoardId(boardCreated.getBoardId());
        commitBoardInput.setTeamId(boardCreated.getCommitToTeamId());

        commitBoardUseCase.execute(commitBoardInput,commitBoardOutput);

    }
}
