package ddd.kanban.usecase.kanbanboard.board.sqlite;

import ddd.kanban.adapter.presenter.kanbanboard.board.create.CreateBoardPresenter;
import ddd.kanban.adapter.presenter.kanbanboard.board.get.GetAllBoardsPresenter;
import ddd.kanban.adapter.repository.board.SqliteBoardRepository;
import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.kanbanboard.board.Board;
import ddd.kanban.usecase.kanbanboard.board.get.GetAllBoardsInput;
import ddd.kanban.usecase.kanbanboard.board.get.GetAllBoardsOutput;
import ddd.kanban.usecase.kanbanboard.board.get.GetAllBoardsUseCase;
import ddd.kanban.usecase.kanbanboard.board.mapper.BoardEntityMapper;
import ddd.kanban.usecase.domainevent.handler.DomainEventHandler;
import ddd.kanban.usecase.kanbanboard.board.create.CreateBoardInput;
import ddd.kanban.usecase.kanbanboard.board.create.CreateBoardOutput;
import ddd.kanban.usecase.kanbanboard.board.create.CreateBoardUseCase;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class SqliteCreateBoardUseCaseTest {

    private BoardRepository sqliteBoardRepository;
    private WorkflowRepository workflowRepository;
    private String boardId;
    private DomainEventBus domainEventBus;

    @Before
    public void setUp(){
        boardId = UUID.randomUUID().toString();
        sqliteBoardRepository = new SqliteBoardRepository();
        this.workflowRepository = new InMemoryWorkflowRepository();
        this.domainEventBus = new DomainEventBus();
        this.domainEventBus.register(new DomainEventHandler(workflowRepository, sqliteBoardRepository, this.domainEventBus));
    }

    @Test
    public void testCreateBoardUseCaseWithSQLite(){
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(sqliteBoardRepository, domainEventBus);
        CreateBoardInput createBoardInput = new CreateBoardInput("TestBoard2", "This is board that save in sqlite");
        CreateBoardOutput createBoardOutput = new CreateBoardPresenter();
        createBoardUseCase.execute(createBoardInput, createBoardOutput);

        Board board = BoardEntityMapper.mappingBoardFrom(sqliteBoardRepository.findById(createBoardOutput.getBoardId()));

        assertEquals("TestBoard2", board.getTitle());
        assertEquals("This is board that save in sqlite", board.getDescription());
    }
}
