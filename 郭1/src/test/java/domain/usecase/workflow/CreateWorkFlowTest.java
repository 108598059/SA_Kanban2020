package domain.usecase.workflow;

import domain.adapters.repository.BoardRepositoryImpl;
import domain.adapters.repository.WorkflowRepositoryImpl;
import domain.adapters.controller.board.input.CreateBoardInputImpl;
import domain.adapters.controller.board.output.CreateBoardOutputImpl;
import domain.adapters.controller.workflow.input.CreateWorkflowInputImpl;
import domain.adapters.controller.workflow.output.CreateWorkflowOutputImpl;
import domain.entity.DomainEventBus;
import domain.entity.aggregate.board.Board;
import domain.entity.aggregate.workflow.Workflow;
import domain.usecase.board.BoardRepository;
import domain.usecase.board.BoardTransformer;
import domain.usecase.board.create.CreateBoardInput;
import domain.usecase.board.create.CreateBoardOutput;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.workflow.create.CreateWorkflowInput;
import domain.usecase.workflow.create.CreateWorkflowOutput;
import domain.usecase.workflow.create.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateWorkFlowTest {

    private String boardId;
    private BoardRepository boardRepository;
    private WorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    @Before
    public void setUp(){

        boardRepository = new BoardRepositoryImpl();
        workflowRepository = new WorkflowRepositoryImpl();
        eventBus = new DomainEventBus();

        CreateBoardInput createBoardInput = new CreateBoardInputImpl();
        CreateBoardOutput createBoardOutput = new CreateBoardOutputImpl();
        createBoardInput.setName("board1");


        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, eventBus);
        createBoardUseCase.execute(createBoardInput,createBoardOutput);


        boardId = createBoardOutput.getBoardId();


        eventBus.register(new WorkflowEventHandler(boardRepository, eventBus));
    }

    @Test
    public void Create_workflow_should_commit_workflow_in_its_board(){
        WorkflowRepository workflowRepository = new WorkflowRepositoryImpl();


        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository,eventBus);
        CreateWorkflowInput createWorkflowInput = new CreateWorkflowInputImpl();
        CreateWorkflowOutput createWorkflowOutput = new CreateWorkflowOutputImpl();

        createWorkflowInput.setWorkflowName("workflow1");
        createWorkflowInput.setBoardId(boardId);

        createWorkflowUseCase.execute(createWorkflowInput, createWorkflowOutput);


        Workflow workflow = WorkflowTransformer.toWorkflow(workflowRepository.getWorkFlowById(createWorkflowOutput.getWorkflowId()));
        assertEquals("workflow1",workflow.getName());


        Board board = BoardTransformer.toBoard(boardRepository.getBoardById(boardId));

        assertEquals(1,board.getWorkflows().size());
        assertEquals(workflow.getId(), board.getWorkflows().get(0));

    }



}
