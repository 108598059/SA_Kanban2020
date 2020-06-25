package ddd.kanban.usecase.kanbanboard.board.create;

import ddd.kanban.adapter.presenter.kanbanboard.board.create.CreateBoardPresenter;
import ddd.kanban.adapter.repository.board.InMemoryBoardRepository;
import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.kanbanboard.board.Board;
import ddd.kanban.domain.model.kanbanboard.workflow.Workflow;
import ddd.kanban.usecase.kanbanboard.board.mapper.BoardEntityMapper;
import ddd.kanban.usecase.domainevent.handler.DomainEventHandler;
import ddd.kanban.usecase.HierarchyInitial;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.kanbanboard.workflow.mapper.WorkflowEntityMapper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateBoardUseCaseTest {
    private BoardRepository boardRepository;
    private WorkflowRepository workflowRepository;
    private DomainEventBus domainEventBus;
    private HierarchyInitial hierarchyInitial;

    @Before
    public void setUp() {
        boardRepository = new InMemoryBoardRepository();
        workflowRepository = new InMemoryWorkflowRepository();

        domainEventBus = new DomainEventBus();
        domainEventBus.register(new DomainEventHandler(workflowRepository, boardRepository, domainEventBus));

        hierarchyInitial = new HierarchyInitial(boardRepository, workflowRepository, domainEventBus);
    }

    @Test
    public void testCreateBoardUseCase() {
        assertEquals(0, boardRepository.findAll().size());

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, domainEventBus);
        CreateBoardInput createBoardInput = new CreateBoardInput("TestBoard", "This is the board that be saved in memory");
        CreateBoardOutput createBoardOutput = new CreateBoardPresenter();
        createBoardUseCase.execute(createBoardInput, createBoardOutput);

        Board board = BoardEntityMapper.mappingBoardFrom(boardRepository.findById(createBoardOutput.getBoardId()));

        assertEquals(createBoardOutput.getBoardId(), board.getId());
        assertEquals("TestBoard", board.getTitle());
        assertEquals("This is the board that be saved in memory", board.getDescription());

        assertEquals(1, boardRepository.findAll().size());
    }

    @Test
    public void testCreateBoardShouldCreateDefaultWorkflowAndThenWorkflowShouldCreateDefaultColumn() {
        assertEquals(0, workflowRepository.findAll().size());

        hierarchyInitial.CreateBoard();

        assertEquals(1, workflowRepository.findAll().size());

        Workflow workflow =  WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findAll().get(0));
        assertEquals("Default workflow", workflow.getTitle());

        assertEquals(1, workflow.getColumns().size());
        assertEquals("Default Column", workflow.getColumns().get(0).getTitle());
    }
}
