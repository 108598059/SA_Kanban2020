package domain.usecase.board;

import domain.adapters.repository.BoardRepositoryImpl;
import domain.adapters.repository.WorkflowRepositoryImpl;
import domain.adapters.controller.board.input.CreateBoardInputImpl;
import domain.adapters.controller.board.output.CreateBoardOutputImpl;
import domain.adapters.controller.workflow.input.CommitWorkflowInputImpl;
import domain.adapters.controller.workflow.output.CommitWorkflowOutputImpl;
import domain.adapters.controller.workflow.input.CreateWorkflowInputImpl;
import domain.adapters.controller.workflow.output.CreateWorkflowOutputImpl;
import domain.entity.DomainEventBus;
import domain.entity.aggregate.board.Board;
import domain.usecase.board.commit.CommitWorkflowInput;
import domain.usecase.board.commit.CommitWorkflowOutput;
import domain.usecase.board.commit.CommitWorkflowUseCase;
import domain.usecase.board.create.CreateBoardInput;
import domain.usecase.board.create.CreateBoardOutput;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.workflow.WorkflowRepository;
import domain.usecase.workflow.create.CreateWorkflowInput;
import domain.usecase.workflow.create.CreateWorkflowOutput;
import domain.usecase.workflow.create.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommitWorkflowTest {

    private String boardId;
    private BoardRepository boardRepository;
    private WorkflowRepository workflowRepository;
    private String workflowId;
    private DomainEventBus eventBus;

    @Before
    public void setUp(){

        boardRepository = new BoardRepositoryImpl();
        eventBus = new DomainEventBus();

        CreateBoardInput createBoardInput = new CreateBoardInputImpl();
        CreateBoardOutput createBoardOutput = new CreateBoardOutputImpl();
        createBoardInput.setName("board1");

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, eventBus);
        createBoardUseCase.execute(createBoardInput,createBoardOutput);

        boardId = createBoardOutput.getBoardId();

        workflowRepository = new WorkflowRepositoryImpl();

        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository,eventBus);
        CreateWorkflowInput createWorkflowInput = new CreateWorkflowInputImpl();
        CreateWorkflowOutput createWorkflowOutput = new CreateWorkflowOutputImpl();

        createWorkflowInput.setWorkflowName("workflow1");
        createWorkflowInput.setBoardId(boardId);

        createWorkflowUseCase.execute(createWorkflowInput, createWorkflowOutput);

        workflowId = createWorkflowOutput.getWorkflowId();

    }

    @Test
    public void CommitWorkflowTest(){


        CommitWorkflowInput commitWorkflowInput = new CommitWorkflowInputImpl();
        CommitWorkflowOutput commitWorkflowOutput = new CommitWorkflowOutputImpl();
        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository, eventBus);

        commitWorkflowInput.setBoardId(boardId);
        commitWorkflowInput.setWorkflowId(workflowId);

        commitWorkflowUseCase.execute(commitWorkflowInput,commitWorkflowOutput);

        Board board = BoardTransformer.toBoard(boardRepository.getBoardById(boardId));

        assertEquals(1,board.getWorkflows().size());
        assertEquals(commitWorkflowOutput.getWorkflowId(), board.getWorkflows().get(0));

    }
}
