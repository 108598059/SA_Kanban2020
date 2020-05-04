package ddd.kanban.usecase.board;

import ddd.kanban.adapter.repository.board.InMemoryBoardRepository;
import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.board.Board;
import ddd.kanban.usecase.DomainEventHandler;
import ddd.kanban.usecase.EntityMapper;
import ddd.kanban.usecase.HierarchyInitial;
import ddd.kanban.usecase.board.commit.CommitWorkflowInput;
import ddd.kanban.usecase.board.commit.CommitWorkflowOutput;
import ddd.kanban.usecase.board.commit.CommitWorkflowUseCase;
import ddd.kanban.usecase.board.create.CreateBoardInput;
import ddd.kanban.usecase.board.create.CreateBoardOutput;
import ddd.kanban.usecase.board.create.CreateBoardUseCase;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.workflow.create.CreateWorkflowInput;
import ddd.kanban.usecase.workflow.create.CreateWorkflowOutput;
import ddd.kanban.usecase.workflow.create.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CommitWorkflowUseCaseTest {

    private BoardRepository boardRepository;
    private WorkflowRepository workflowRepository;
    private String boardId;
    private DomainEventBus domainEventBus;
    private HierarchyInitial hierarchyInitial;
    private EntityMapper entityMapper;

    @Before
    public void setUp(){
        boardRepository = new InMemoryBoardRepository();
        this.workflowRepository = new InMemoryWorkflowRepository();
        this.domainEventBus = new DomainEventBus();
        this.domainEventBus.register(new DomainEventHandler(workflowRepository, boardRepository, domainEventBus));
        hierarchyInitial = new HierarchyInitial(boardRepository, workflowRepository, domainEventBus);
        boardId = hierarchyInitial.CreateBoard();
        this.entityMapper = new EntityMapper();
    }

    @Test
    public void testCreateBoardShouldCreateWorkflowAndCommitBackToBoard(){
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, domainEventBus);
        CreateBoardInput createBoardInput = new CreateBoardInput("board", "Test");
        CreateBoardOutput createBoardOutput = new CreateBoardOutput();

        createBoardUseCase.execute(createBoardInput, createBoardOutput);

        Board board = entityMapper.mappingBoardEntityFrom(boardRepository.findById(createBoardOutput.getBoardId()));
        assertEquals(1, board.getWorkflowIds().size());
    }

    @Test
    public void testCreateWorkflowShouldCommitToBoard(){
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, domainEventBus);
        CreateWorkflowInput createWorkflowInput = new CreateWorkflowInput("Workflow2", boardId);
        CreateWorkflowOutput createWorkflowOutput = new CreateWorkflowOutput();

        createWorkflowUseCase.execute(createWorkflowInput, createWorkflowOutput);

        Board board = entityMapper.mappingBoardEntityFrom(boardRepository.findById(boardId));
        assertEquals(2, board.getWorkflowIds().size());
    }

    @Test
    public void testCommitWorkflow(){
        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository);
        CommitWorkflowInput commitWorkflowInput = new CommitWorkflowInput(boardId, UUID.randomUUID().toString());
        CommitWorkflowOutput commitWorkflowOutput = new CommitWorkflowOutput();

        commitWorkflowUseCase.execute(commitWorkflowInput, commitWorkflowOutput);

        Board board = entityMapper.mappingBoardEntityFrom(boardRepository.findById(boardId));
        assertEquals(2, board.getWorkflowIds().size());
    }
}