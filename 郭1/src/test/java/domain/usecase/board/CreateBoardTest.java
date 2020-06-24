package domain.usecase.board;


import domain.adapters.controller.board.input.CreateBoardInputImpl;
import domain.adapters.controller.board.output.CreateBoardOutputImpl;
import domain.adapters.repository.BoardRepositoryImpl;
import domain.adapters.repository.TeamRepositoryImpl;
import domain.entity.DomainEventBus;
import domain.entity.aggregate.board.Board;
import domain.entity.aggregate.team.Team;
import domain.usecase.board.create.CreateBoardInput;
import domain.usecase.board.create.CreateBoardOutput;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.team.TeamRepository;
import domain.usecase.team.TeamTransformer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateBoardTest {

    private DomainEventBus eventBus;
    private BoardRepository boardRepository;
    private TeamRepository teamRepository;

    private String teamId;

    @Before
    public void setUp(){
        eventBus = new DomainEventBus();
        boardRepository = new BoardRepositoryImpl();
        teamRepository = new TeamRepositoryImpl();

        Team team = new Team("TestCreateBoardTeam");
        teamId = team.getId();
        teamRepository.add(TeamTransformer.toDTO(team));
        eventBus.register(new BoardEventHandler(teamRepository,eventBus));
    }

    @Test
    public void create_board_and_the_board_should_be_committed_to_a_specified_team_Test(){

        CreateBoardInput createBoardInput = new CreateBoardInputImpl();
        CreateBoardOutput createBoardOutput = new CreateBoardOutputImpl();
        createBoardInput.setName("kanban");
        createBoardInput.setTeamId(teamId);

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, eventBus);
        createBoardUseCase.execute(createBoardInput,createBoardOutput);

        // check did board really been saved
        Board board = BoardTransformer.toBoard(boardRepository.getBoardById(createBoardOutput.getBoardId()));
        assertEquals(createBoardOutput.getBoardId(), board.getId());

        // check did board really been committed to team
        Team tempTeam = TeamTransformer.toTeam(teamRepository.getTeamById(teamId));
        assertTrue(tempTeam.isContainBoard(createBoardOutput.getBoardId()));

    }
}
